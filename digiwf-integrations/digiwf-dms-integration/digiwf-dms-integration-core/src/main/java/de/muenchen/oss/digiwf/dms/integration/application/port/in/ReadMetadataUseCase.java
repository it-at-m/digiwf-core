package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import jakarta.validation.constraints.NotBlank;

public interface ReadMetadataUseCase {

    Metadata readMetadata(@NotBlank final ObjectType objectclass, @NotBlank final String coo);

}
