package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("groups-ldap")
@Slf4j
public class LdapAdapter extends LdapTemplate implements ResolveUserGroupsOutPort {
    static final String GROUP_CACHE = "userGroups";
    private final LdapProperties properties;

    public LdapAdapter(final ContextSource contextSource, final LdapProperties properties) {
        super(contextSource);
        this.properties = properties;
    }

    @Override
    @NonNull
    @Cacheable(GROUP_CACHE)
    public List<Group> resolveGroups(@NonNull final String username) {
        log.debug("Resolving groups for user via ldap: {}", username);
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
        log.debug("Resolved groups for user {}: {}", username, groups);
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
        log.debug("Resolved user {} to dn {}", username, userDn);
        return userDn;
    }
}
