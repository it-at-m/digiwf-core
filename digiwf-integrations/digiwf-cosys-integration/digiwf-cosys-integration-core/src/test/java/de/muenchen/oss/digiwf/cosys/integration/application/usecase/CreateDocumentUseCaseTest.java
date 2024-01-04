package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.cosys.integration.adapter.out.ProcessAdapter;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.SaveFileToStoragePort;
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

    private final GenerateDocumentPort generateDocumentPort = mock(GenerateDocumentPort.class);

    private final SaveFileToStoragePort saveFileToStoragePort = mock(SaveFileToStoragePort.class);

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination"
    );

    private final CorrelateMessagePort correlateMessagePort = new ProcessAdapter(processApi);

    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private JsonNode variables = new ObjectMapper().readTree("{\"key1\":\"value\"}");

    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", variables, listOfURls);

    CreateDocumentUseCaseTest() throws JsonProcessingException {
    }

    @Test
    void createDocument() {
        when(generateDocumentPort.generateCosysDocument(any())).thenReturn(Mono.just("Document".getBytes()));

        final CreateDocumentUseCase useCase = new CreateDocumentUseCase(saveFileToStoragePort, correlateMessagePort, generateDocumentPort);
        useCase.createDocument("processInstanceIde", "messageName", generateDocument);

        verify(generateDocumentPort).generateCosysDocument(generateDocument);
        verifyNoMoreInteractions(generateDocumentPort);

        verify(saveFileToStoragePort).saveDocumentInStorage(generateDocument, "Document".getBytes());
        verifyNoMoreInteractions(saveFileToStoragePort);
    }

}
