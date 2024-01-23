package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadMetadataUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadMetadataPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class ReadMetadataService implements ReadMetadataUseCase {

    private final ReadMetadataPort readMetadataPort;

    private final DmsUserPort dmsUserPort;

    @Override
    public Metadata readMetadata(
            @NotBlank final ObjectType objectclass,
            @NotBlank final String coo
    ){

        String user = dmsUserPort.getDmsUser();

        if (objectclass == ObjectType.Schriftstueck) {
            return readMetadataPort.readContentMetadata(coo, user);
        }

        // TODO Überprüfung richtes Objekt
        return readMetadataPort.readMetadata(coo, "user");

    }

}
