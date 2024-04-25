package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.List;

@Slf4j
public class LdapMockAdapter implements ResolveUserGroupsOutPort {
    @Override
    @NonNull
    public List<Group> resolveGroups(@NonNull final String username) {
        log.debug("Resolving groups for user via mock: {}", username);
        return List.of(
                new Group("digiwf-webapp-user")
        );
    }
}
