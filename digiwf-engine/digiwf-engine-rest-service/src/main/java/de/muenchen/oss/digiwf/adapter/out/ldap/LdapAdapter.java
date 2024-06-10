package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.GroupsOutPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import de.muenchen.oss.digiwf.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("groups-ldap")
@Slf4j
@Validated
public class LdapAdapter extends LdapTemplate implements ResolveUserGroupsOutPort, ResolveUserOutPort, GroupsOutPort {
    private final LdapProperties properties;

    public LdapAdapter(final ContextSource contextSource, final LdapProperties properties) {
        super(contextSource);
        this.properties = properties;
    }

    @Override
    @NonNull
    @Cacheable(LdapCacheConfiguration.USER_GROUPS_CACHE)
    public List<Group> resolveUserGroups(@NonNull final String username) {
        log.debug("Resolving groups for user: {}", username);
        String userDn = resolveUserDn(username);
        // build query
        LdapQuery query = LdapQueryBuilder.query().base(properties.getGroupBase())
                .where("objectclass").is("group")
                .and("member").is(userDn);
        // get groups
        log.trace("Resolving groups for userDn {}", userDn);
        List<List<String>> groupDns = super.search(query, (AttributesMapper<List<String>>) attrs -> {
            val groups = new ArrayList<String>();
            // group cn
            groups.add((String) attrs.get("distinguishedName").get());
            // parent group cns for 1 level of recursion
            if (attrs.get("memberOf") != null) {
                attrs.get("memberOf").getAll().asIterator().forEachRemaining((i) -> {
                    if (((String) i).endsWith(properties.getGroupBase())) groups.add((String) i);
                });
            }
            return groups;
        });
        log.trace("Resolved groupDns for user {}: {}", username, groupDns);
        // map
        val groups = groupDns.stream()
                .flatMap(List::stream).distinct().sorted()
                // map dn to cn
                .map(str -> {
                    LdapName ldapName;
                    try {
                        ldapName = new LdapName(str);
                    } catch (InvalidNameException e) {
                        throw new IllegalStateException("Error while parsing ldap group dns", e);
                    }
                    return ldapName.getRdns().stream()
                            .filter(i -> i.getType().equalsIgnoreCase("CN"))
                            .map(Rdn::getValue).map(String::valueOf).findFirst().orElseThrow();
                })
                // map to group
                .map(str -> Group.builder().name(str).build())
                .toList();
        log.info("Resolved groups for user {}: {}", username, groups);
        return groups;
    }

    /**
     * Resolves username to full distinguishedName (dn).
     *
     * @param username The username to resolve.
     * @return The full dn of the user.
     */
    private String resolveUserDn(@NonNull final String username) {
        log.trace("Resolving dn for user: {}", username);
        LdapQuery query = LdapQueryBuilder.query()
                .base(properties.getUserBase())
                .where("objectclass").is("user")
                .and("cn").is(username);
        List<String> result = super.search(query, (AttributesMapper<String>) attrs -> attrs.get("distinguishedName").get().toString());
        if (result.isEmpty()) {
            log.error("Username {} not found", username);
            throw new IllegalStateException(String.format("Username '%s' not found via ldap adapter", username));
        }
        if (result.size() > 1) {
            log.error("Username {} found more than once", username);
            throw new IllegalStateException(String.format("Multiple users found for username '%s'", username));
        }
        val userDn = result.get(0);
        log.info("Resolved user {} to dn {}", username, userDn);
        return userDn;
    }

    @Override
    @Cacheable(LdapCacheConfiguration.USER_CACHE)
    public User resolveUser(@NonNull final String username) {
        log.trace("Resolving user: {}", username);
        LdapQuery query = LdapQueryBuilder.query()
                .base(properties.getUserBase())
                .where("objectclass").is("user")
                .and("cn").is(username);
        List<User> result = super.search(query, (AttributesMapper<User>) attrs ->
                User.builder().id(username)
                        .firstName(attrs.get("givenName").get().toString())
                        .lastName(attrs.get("sn").get().toString())
                        .email(attrs.get("mail").get().toString())
                        .build()
        );
        if (result.isEmpty()) {
            log.info("Username {} not found", username);
            return null;
        }
        if (result.size() > 1) {
            log.error("Username {} found more than once", username);
            throw new IllegalStateException(String.format("Multiple users found for username '%s'", username));
        }
        val user = result.get(0);
        log.info("Resolved user {} to {}", username, user);
        return user;
    }

    @NonNull
    @Override
    @Cacheable(LdapCacheConfiguration.GROUPS_MEMBERS)
    public List<String> getGroupsMembers(@NonNull @NotEmpty final List<Group> groups) {
        val resolvedGroups = resolveGroups(groups);
        val resolvedGroupsCn = resolvedGroups.stream()
                .flatMap(i -> i.subGroups().stream())
                .map(this::dnToCn)
                .map(Group::new).toList();
        log.info("Resolved {} engine groups to {} ldap groups", groups.size(), resolvedGroups.size());
        // resolve one level of recursion
        resolvedGroups.addAll(resolveGroups(resolvedGroupsCn));
        // map groups with users to unique users
        return resolvedGroups.stream()
                .flatMap(i -> i.users().stream())
                .map(this::dnToCn)
                .distinct().sorted().toList();
    }

    /**
     * Resolves a list of groups to subgroups and member users.
     *
     * @param groups List of groups to resolve.
     * @return List of LdapUsers with corresponding subgroups and member users.
     */
    private List<LdapGroup> resolveGroups(@NonNull @NotEmpty final List<Group> groups) {
        log.trace("Resolving groups {}", groups);
        // build ldap search filter and query
        val groupNameFilter = new OrFilter();
        for (Group group : groups) {
            groupNameFilter.or(new EqualsFilter("cn", group.name()));
        }
        val filter = new AndFilter();
        filter.and(groupNameFilter);
        filter.and(new EqualsFilter("objectclass", "group"));
        LdapQuery query = LdapQueryBuilder.query()
                .base(properties.getGroupBase())
                .filter(filter);
        // search and resolve groups
        return super.search(query, (AttributesMapper<LdapGroup>) attrs -> {
            val users = new ArrayList<String>(List.of());
            val childGroups = new ArrayList<String>(List.of());
            if (attrs.get("member") != null) {
                attrs.get("member").getAll().asIterator().forEachRemaining((i) -> {
                    if (((String) i).endsWith(properties.getUserBase()))
                        users.add(i.toString());
                    else if (((String) i).endsWith(properties.getGroupBase())) {
                        childGroups.add(i.toString());
                    }
                });
            }
            return new LdapGroup(attrs.get("cn").get().toString(), users, childGroups);
        });
    }

    /**
     * Converts a ldap dn to a cn.
     *
     * @param dn The dn to convert.
     * @return The extracted cn.
     */
    @SneakyThrows
    private String dnToCn(@NonNull final String dn) {
        val parsedDn = new LdapName(dn);
        return parsedDn.getRdns().stream()
                .filter(i -> i.getType().equalsIgnoreCase("CN"))
                .map(i -> i.getValue().toString())
                .findFirst().orElseThrow();
    }

    private record LdapGroup(String cn, List<String> users, List<String> subGroups) {
    }
}
