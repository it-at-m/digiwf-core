package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.ArrayList;
import java.util.List;

public class LdapOutAdapter extends LdapTemplate implements ResolveUserGroupsOutPort {
    private final LdapProperties properties;

    public LdapOutAdapter(final ContextSource contextSource, final LdapProperties properties) {
        super(contextSource);
        this.properties = properties;
    }

    @Override
    @NonNull
    @Cacheable("userGroups")
    public List<Group> resolveGroups(@NonNull final String username) {
        String userDn = resolveUserDn(username);
        // build query
        LdapQuery query = LdapQueryBuilder.query().base(properties.getGroupBase())
                .where("objectclass").is("group")
                .and("member").is(userDn);
        // get groups
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
        // map
        return groupDns.stream()
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
    }

    /**
     * Resolves username to full distinguishedName (dn).
     *
     * @param username The username to resolve.
     * @return The full dn of the user.
     */
    private String resolveUserDn(@NonNull final String username) {
        LdapQuery query = LdapQueryBuilder.query()
                .base(properties.getUserBase())
                .where("objectclass").is("user")
                .and("cn").is(username);
        List<String> result = super.search(query, (AttributesMapper<String>) attrs -> attrs.get("distinguishedName").get().toString());
        if (result.isEmpty())
            throw new UsernameNotFoundException(String.format("Username '%s' not found via ldap adapter.", username));
        if (result.size() > 1)
            throw new IllegalStateException(String.format("Multiple users found for username '%s'", username));
        return result.get(0);
    }
}
