package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.cosys.integration.adapter.out.ProcessAdapter;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.CorrelateMessageOutPort;
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

    private final MessageApi messageApi = spy(mock(MessageApi.class));

    private final GenerateDocumentOutPort generateDocumentOutPort = mock(GenerateDocumentOutPort.class);

    private final SaveFileToStorageOutPort saveFileToStorageOutPort = mock(SaveFileToStorageOutPort.class);

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination"
    );

    private final CorrelateMessageOutPort correlateMessageOutPort = new ProcessAdapter(processApi);

    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private final List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private final JsonNode variables = new ObjectMapper().readTree("{\"key1\":\"value\"}");

    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", variables, listOfURls);

    CreateDocumentUseCaseTest() throws JsonProcessingException {
    }

    @Test
    void createDocument() {
        when(generateDocumentOutPort.generateCosysDocument(any())).thenReturn(Mono.just("Document".getBytes()));

        final CreateDocumentUseCase useCase = new CreateDocumentUseCase(saveFileToStorageOutPort, correlateMessageOutPort, generateDocumentOutPort);
        useCase.createDocument("processInstanceIde", "type", "integrationName", generateDocument);

        verify(generateDocumentOutPort).generateCosysDocument(generateDocument);
        verifyNoMoreInteractions(generateDocumentOutPort);

        verify(saveFileToStorageOutPort).saveDocumentInStorage(generateDocument, "Document".getBytes());
        verifyNoMoreInteractions(saveFileToStorageOutPort);
    }

}
