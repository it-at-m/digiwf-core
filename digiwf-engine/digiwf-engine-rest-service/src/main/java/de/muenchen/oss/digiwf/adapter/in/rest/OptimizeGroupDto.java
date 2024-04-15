package de.muenchen.oss.digiwf.adapter.in.rest;

import de.muenchen.oss.digiwf.domain.Group;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizeGroupDto {
    private final String id;
    private final String name;
    private final String type;

    static OptimizeGroupDto fromGroup(Group group) {
        return builder()
                .type("role")
                .id(group.name())
                .name(group.name())
                .build();
    }
}
