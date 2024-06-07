package de.muenchen.oss.digiwf.adapter.in.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizeUserDto {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
}
