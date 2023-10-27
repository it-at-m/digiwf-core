package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadContentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadContent;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferContentPort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadContentService implements ReadContentUseCase {

    private final TransferContentPort transferContentPort;
    private final ReadContent readContent;

    @Override
    public void readContent(final List<String> contentCoos, final String user, final String filePath, final String fileContext) {
        val content = readContent.readContent(contentCoos, user);
        transferContentPort.transferContent(content, filePath, fileContext);
    }
}
