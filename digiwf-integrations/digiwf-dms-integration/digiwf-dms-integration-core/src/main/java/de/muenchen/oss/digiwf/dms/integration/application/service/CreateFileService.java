package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateFileUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@RequiredArgsConstructor
public class CreateFileService implements CreateFileUseCase {

    private final CreateFilePort createFilePort;

    @Override
    public String createFile(
            @NotBlank final String titel,
            @NotBlank final String apentryCOO,
            @NotBlank final String user
    ){
        final File file = new File(apentryCOO,titel);

        return createFilePort.createFile(file,user);
    }

}
