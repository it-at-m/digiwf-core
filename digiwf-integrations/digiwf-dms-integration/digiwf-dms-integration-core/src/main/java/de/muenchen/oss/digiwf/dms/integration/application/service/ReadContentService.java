package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadContentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadContentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferContentPort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RequiredArgsConstructor
public class ReadContentService implements ReadContentUseCase {

    private final TransferContentPort transferContentPort;
    private final ReadContentPort readContentPort;

    @Override
    public void readContent(
            final List<String> contentCoos,
            @NotBlank final String user,
            @NotBlank final String filePath,
            @NotBlank final String fileContext) {
        val content = readContentPort.readContent(contentCoos, user);
        transferContentPort.transferContent(content, filePath, fileContext);
    }
}
