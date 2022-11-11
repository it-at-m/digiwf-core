package io.muenchendigital.digiwf.verification.integration.shared.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity representation of a verification.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Verification")
@Table(name = "VERIFICATION",
        uniqueConstraints=@UniqueConstraint(columnNames = {"PROCESS_INSTANCE_ID", "MESSAGE_NAME"}),
        indexes = @Index(name = "IDX_PROCINSTID_MSGNAME", columnList = "PROCESS_INSTANCE_ID, MESSAGE_NAME")
)
public class VerificationEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "PROCESS_INSTANCE_ID", nullable = false)
    private String processInstanceId;

    @Column(name = "MESSAGE_NAME", nullable = false)
    private String messageName;

    @Column(name = "EXPIRY_TIME")
    private java.time.LocalDateTime expiryTime;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

}
