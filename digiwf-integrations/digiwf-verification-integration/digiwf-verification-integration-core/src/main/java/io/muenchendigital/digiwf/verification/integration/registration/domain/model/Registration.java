package io.muenchendigital.digiwf.verification.integration.registration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data for a verification registration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    private String processInstanceId;

    private String messageName;

    private java.time.LocalDateTime expiryTime;

    private String subject;
}
