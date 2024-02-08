package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadMetadataUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadMetadataPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class ReadMetadataService implements ReadMetadataUseCase {

    private final ReadMetadataPort readMetadataPort;

    private final DmsUserPort dmsUserPort;

    @Override
    public Metadata readMetadata(
            @NotNull final ObjectType objectclass,
            @NotBlank final String coo
    ){

        String user = dmsUserPort.getDmsUser();

        if (objectclass == ObjectType.Schriftstueck) {
            return readMetadataPort.readContentMetadata(coo, user);
        }

        Metadata metadata = readMetadataPort.readMetadata(coo, user);

        String object = objectclass == ObjectType.Intern ?
                "Internes Dokument" :
                objectclass.toString();

        if(!object.equals(metadata.getType())){
            throw new BpmnError("AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE","Das übergebene Objekt mit der COO-Adresse " + coo + " ist ungültig, da das übergebene Objekt von der Objektklasse " + metadata.getType() + " ist und dies nicht mit der/den erwarteten Objektklasse/n " + objectclass + " übereinstimmt.");
        }
        return metadata;

    }

}
