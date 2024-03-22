package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadMetadataInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadMetadataOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class ReadMetadataUseCase implements ReadMetadataInPort {

    private final ReadMetadataOutPort readMetadataOutPort;

    private final DmsUserOutPort dmsUserOutPort;

    @Override
    public Metadata readMetadata(
            @NotNull final ObjectType objectclass,
            @NotBlank final String coo
    ) {

        String user = dmsUserOutPort.getDmsUser();

        if (objectclass == ObjectType.Schriftstueck) {
            return readMetadataOutPort.readContentMetadata(coo, user);
        }

        Metadata metadata = readMetadataOutPort.readMetadata(coo, user);

        String object = objectclass == ObjectType.Intern ?
                "Internes Dokument" :
                objectclass.toString();

        if (!object.equals(metadata.getType())) {
            throw new BpmnError("AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE", "Das übergebene Objekt mit der COO-Adresse " + coo + " ist ungültig, da das übergebene Objekt von der Objektklasse " + metadata.getType() + " ist und dies nicht mit der/den erwarteten Objektklasse/n " + objectclass + " übereinstimmt.");
        }
        return metadata;

    }

}
