package de.muenchen.oss.digiwf.application.usecase;

import de.muenchen.oss.digiwf.application.port.in.ResolveUserInPort;
import de.muenchen.oss.digiwf.application.port.out.EngineAuthorizationsOutPort;
import de.muenchen.oss.digiwf.application.port.out.GroupsOutPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserOutPort;
import de.muenchen.oss.digiwf.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Profile({"groups-ldap", "groups-mock"})
@RequiredArgsConstructor
public class ResolveUserUseCase implements ResolveUserInPort {
    private final EngineAuthorizationsOutPort engineAuthorizationsOutPort;
    private final GroupsOutPort groupsOutPort;
    private final ResolveUserOutPort resolveUserOutPort;

    @Override
    public User resolveUser(@NonNull final String userName) {

        val authorizedGroups = engineAuthorizationsOutPort.getOptimizeAuthorizedGroups();
        val authorizedUsers = groupsOutPort.getGroupsMembers(authorizedGroups);

        if (!authorizedUsers.contains(userName))
            return null;

        return resolveUserOutPort.resolveUser(userName);
    }
}
