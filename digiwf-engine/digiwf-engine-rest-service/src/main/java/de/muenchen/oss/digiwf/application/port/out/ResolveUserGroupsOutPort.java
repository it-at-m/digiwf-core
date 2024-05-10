package de.muenchen.oss.digiwf.application.port.out;

import de.muenchen.oss.digiwf.domain.Group;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ResolveUserGroupsOutPort {

    @NonNull
    List<Group> resolveGroups(@NonNull final String username);
}
