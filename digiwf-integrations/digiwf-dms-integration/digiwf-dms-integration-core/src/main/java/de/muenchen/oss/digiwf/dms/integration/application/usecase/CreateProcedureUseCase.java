package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateProcedureInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateProcedureOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class CreateProcedureUseCase implements CreateProcedureInPort {

    private final CreateProcedureOutPort createProcedureOutPort;

    @Override
    public Procedure createProcedure(
            @NotBlank final String titel,
            @NotBlank final String fileCOO,
            final String fileSubj,
            @NotBlank final String user) {
        final Procedure procedure = new Procedure(fileCOO, titel, fileSubj);

        return createProcedureOutPort.createProcedure(procedure, user);
    }
}
