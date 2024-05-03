package de.muenchen.oss.digiwf.application.port.in;

import de.muenchen.oss.digiwf.domain.Group;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ResolveUserGroupsInPort {

    @NonNull
    List<Group> resolveGroups(@NonNull final String username);
}
