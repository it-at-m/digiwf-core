package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import org.springframework.lang.NonNull;

import java.util.List;

public class LdapMockOutPort implements ResolveUserGroupsOutPort {
    @Override
    @NonNull
    public List<Group> resolveGroups(@NonNull final String username) {
        return List.of();
    }
}
