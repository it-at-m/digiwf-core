package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.SearchSubjectAreaUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchSubjectAreaPort;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public class SearchSubjectAreaService implements SearchSubjectAreaUseCase {

    private final SearchSubjectAreaPort searchSubjectAreaPort;

    @Override
    public String searchSubjectArea(String searchString, String user) {

        val subjectAreas = searchSubjectAreaPort.searchSubjectArea(searchString, user);

        if (subjectAreas.isEmpty()) {
            throw new BpmnError("OBJECT_NOT_FOUND", String.format("Subject Area not found with searchString %s and user %s", searchString, user));
        }

        // return first result
        return subjectAreas.get(0);
    }
}
