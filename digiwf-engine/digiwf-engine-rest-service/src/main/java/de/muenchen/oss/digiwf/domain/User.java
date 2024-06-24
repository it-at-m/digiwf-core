package de.muenchen.oss.digiwf.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
}
