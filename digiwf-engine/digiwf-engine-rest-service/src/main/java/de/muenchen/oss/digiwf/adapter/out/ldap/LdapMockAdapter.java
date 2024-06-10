package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.application.port.out.GroupsOutPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import de.muenchen.oss.digiwf.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("groups-mock")
@Slf4j
public class LdapMockAdapter implements ResolveUserGroupsOutPort, ResolveUserOutPort, GroupsOutPort {
    @Override
    @NonNull
    public List<Group> resolveUserGroups(@NonNull final String username) {
        log.debug("Resolving groups for user via mock: {}", username);
        return List.of(
                new Group("digiwf-webapp-user")
        );
    }

    @Override
    public User resolveUser(@NonNull final String username) {
        return User.builder().id(username).build();
    }

    @NonNull
    @Override
    public List<String> getGroupsMembers(@NonNull @NotEmpty final List<Group> groups) {
        return List.of("johndoe", "janedoe");
    }
}
