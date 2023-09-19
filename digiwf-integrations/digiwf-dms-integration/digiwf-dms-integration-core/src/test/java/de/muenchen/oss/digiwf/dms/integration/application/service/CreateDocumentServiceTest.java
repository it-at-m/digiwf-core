package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateDocumentServiceTest {

    private final LoadFilePort loadFilePort = mock(LoadFilePort.class);

    private final ProcedureRepository procedureRepository = mock(ProcedureRepository.class);

    private final CreateDocumentService createDocumentService = new CreateDocumentService(procedureRepository, loadFilePort);

    @Test
    void createDocument() {

        Content content = new Content("extension", "name", "content".getBytes());

        List<String> filepaths = List.of("path/content.pdf");

        when(this.loadFilePort.loadFiles(any(),any(),any())).thenReturn(List.of(content));

        when(this.procedureRepository.createDocument(any(),any())).thenReturn("documentCOO");

        createDocumentService.createDocument("procedureCOO","title","user", DocumentType.EINGEHEND,filepaths, "filecontext","processInstance");

        verify(this.loadFilePort, times(1)).loadFiles(filepaths, "filecontext","processInstance");

        verify(this.procedureRepository, times(1)).createDocument(new Document("procedureCOO","title",DocumentType.EINGEHEND,List.of(content)),"user");

    }
}