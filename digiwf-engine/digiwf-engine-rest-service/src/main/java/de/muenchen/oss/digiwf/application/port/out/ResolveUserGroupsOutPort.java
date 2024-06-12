package de.muenchen.oss.digiwf.application.port.out;

import de.muenchen.oss.digiwf.domain.Group;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ResolveUserGroupsOutPort {

    /**
     * Resolve groups a user is member of.
     *
     * @param username The for which the groups should be resolved.
     * @return The groups the user is member of.
     */
    @NonNull
    List<Group> resolveUserGroups(@NonNull final String username);
}
