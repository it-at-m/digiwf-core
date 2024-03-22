package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateFileInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class CreateFileUseCase implements CreateFileInPort {

    private final CreateFileOutPort createFileOutPort;

    @Override
    public String createFile(
            @NotBlank final String titel,
            @NotBlank final String apentryCOO,
            @NotBlank final String user
    ) {
        final File file = new File(apentryCOO, titel);

        return createFileOutPort.createFile(file, user);
    }

}
