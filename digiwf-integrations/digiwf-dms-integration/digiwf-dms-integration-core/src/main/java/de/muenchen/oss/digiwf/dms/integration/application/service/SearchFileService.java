package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.SearchFileUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class SearchFileService implements SearchFileUseCase {

    private final SearchFilePort searchFilePort;

    @Override
    public File searchFile(String searchString, String user) {

        val files = searchFilePort.searchFile(searchString, user);

        if (files.isEmpty()) {
            throw new BpmnError("FILE_NOT_FOUND", String.format("File not found with searchString %s and user %s", searchString, user));
        }

        // return first result
        return files.get(0);
    }
}
