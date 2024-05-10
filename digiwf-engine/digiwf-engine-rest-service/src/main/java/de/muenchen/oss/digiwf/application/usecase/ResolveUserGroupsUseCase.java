package de.muenchen.oss.digiwf.application.usecase;

import de.muenchen.oss.digiwf.application.port.in.ResolveUserGroupsInPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"groups-ldap", "groups-mock"})
@RequiredArgsConstructor
public class ResolveUserGroupsUseCase implements ResolveUserGroupsInPort {
    private final ResolveUserGroupsOutPort resolveUserGroupsOutPort;

    @Override
    @NonNull
    public List<Group> resolveGroups(@NonNull final String username) {
        return resolveUserGroupsOutPort.resolveGroups(username);
    }
}
