package de.muenchen.oss.digiwf.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    private final String id;
    private final String name;
    private final String type;
}
