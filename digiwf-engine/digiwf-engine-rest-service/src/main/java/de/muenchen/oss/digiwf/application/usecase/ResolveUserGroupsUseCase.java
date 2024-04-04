package de.muenchen.oss.digiwf.application.usecase;

import de.muenchen.oss.digiwf.application.port.in.ResolveUserGroupsInPort;
import de.muenchen.oss.digiwf.application.port.out.UserToGroupResolverOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResolveUserGroupsUseCase implements ResolveUserGroupsInPort {

    private static final Group OPTIMIZE_GROUP = Group.builder()
        .id("optimizeusers")
        .name("Optimize Users")
        .type("role")
        .build();

    private final UserToGroupResolverOutPort userToGroupResolverOutPort;


    @Override
    @NonNull
    public List<Group> resolveGroups(@NonNull String username) {
        var groups = new ArrayList<Group>();
        if (userToGroupResolverOutPort.isUserInOptimizeGroup(username)) {
            groups.add(OPTIMIZE_GROUP);
        }
        return groups;
    }
}
