package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.SearchFileUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
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

        return files.get(0);
    }
}
