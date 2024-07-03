package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentOutPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStorageOutPort;
import de.muenchen.oss.digiwf.cosys.integration.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.impl.ProcessApiImpl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

class CreateDocumentUseCaseTest {

    private final MessageApi messageApi = mock(MessageApi.class);

    private final GenerateDocumentOutPort generateDocumentOutPort = mock(GenerateDocumentOutPort.class);

    private final SaveFileToStorageOutPort saveFileToStorageOutPort = mock(SaveFileToStorageOutPort.class);

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination"
    );

    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private final List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", new ObjectMapper().readTree("{\"key1\":\"value\"}"), listOfURls);

    CreateDocumentUseCaseTest() throws JsonProcessingException {
    }

    @Test
    void createDocument() {
        when(generateDocumentOutPort.generateCosysDocument(any())).thenReturn(Mono.just("Document".getBytes()));

        final CreateDocumentUseCase useCase = new CreateDocumentUseCase(saveFileToStorageOutPort, generateDocumentOutPort);
        useCase.createDocument(generateDocument);

        verify(generateDocumentOutPort).generateCosysDocument(generateDocument);
        verifyNoMoreInteractions(generateDocumentOutPort);

        verify(saveFileToStorageOutPort).saveDocumentInStorage(generateDocument, "Document".getBytes());
        verifyNoMoreInteractions(saveFileToStorageOutPort);
    }

}
