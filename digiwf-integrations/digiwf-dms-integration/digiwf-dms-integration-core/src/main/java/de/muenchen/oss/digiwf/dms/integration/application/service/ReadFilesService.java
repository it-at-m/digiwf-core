package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadFilesUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadFilesPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferFilePort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadFilesService implements ReadFilesUseCase {

    private final TransferFilePort transferFilePort;
    private final ReadFilesPort readFilesPort;

    @Override
    public void readFiles(final List<String> fileCoos, final String user, final String filePath, final String fileContext) {
        val files = readFilesPort.readFiles(fileCoos, user);
        transferFilePort.transferFiles(files, filePath, fileContext);
    }
}
