package de.muenchen.oss.digiwf.application.port.out;

import de.muenchen.oss.digiwf.domain.Group;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GroupsOutPort {
    /**
     * Resolves a list of groups to a list of all unique members.
     *
     * @param groups List of groups to resolve to members.
     * @return Unique list of members.
     */
    @NonNull
    List<String> getGroupsMembers(@NonNull @NotEmpty final List<Group> groups);
}
