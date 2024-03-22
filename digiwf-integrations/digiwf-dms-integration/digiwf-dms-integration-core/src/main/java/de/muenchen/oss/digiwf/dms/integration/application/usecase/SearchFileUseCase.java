package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.SearchFileInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchFileOutPort;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@RequiredArgsConstructor
public class SearchFileUseCase implements SearchFileInPort {

    private final SearchFileOutPort searchFileOutPort;

    @Override
    public List<String> searchFile(final String searchString, final String user, final String reference, final String value) {


        val files = searchFileOutPort.searchFile(searchString, user, reference, value);

        if (files.isEmpty()) {
            throw new BpmnError("OBJECT_NOT_FOUND", String.format("File not found with searchString %s and user %s", searchString, user));
        }

        return files;
    }
}
