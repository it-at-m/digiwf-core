package de.muenchen.oss.digiwf.application.port.out;

import de.muenchen.oss.digiwf.domain.Group;
import org.springframework.lang.NonNull;

import java.util.List;


public interface EngineAuthorizationsOutPort {
    /**
     * Get a list of groups which are authorized to access optimize.
     *
     * @return List of authorized groups.
     */
    @NonNull
    List<Group> getOptimizeAuthorizedGroups();
}
