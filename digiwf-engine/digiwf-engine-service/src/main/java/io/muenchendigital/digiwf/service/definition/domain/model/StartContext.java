package io.muenchendigital.digiwf.service.definition.domain.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

/**
 * Entity representation of a Start Context.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StartContext")
@Table(name = "DWF_START_CONTEXT", indexes = {@Index(name = "IDX_DWF_CONTEXT_USER", columnList = "userid_, definitionkey_")})
public class StartContext {

    public StartContext(final String userId, final String definitionKey) {
        this(userId, definitionKey, null);
    }

    public StartContext(final String userId, final String definitionKey, final String fileContext) {
        this.userId = userId;
        this.definitionKey = definitionKey;
        this.fileContext = Optional.ofNullable(fileContext).orElse(UUID.randomUUID().toString());
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "userid_", nullable = false)
    private String userId;

    @Column(name = "definitionkey_", nullable = false)
    private String definitionKey;

    @Column(name = "filecontext_", nullable = false)
    private String fileContext;

}
