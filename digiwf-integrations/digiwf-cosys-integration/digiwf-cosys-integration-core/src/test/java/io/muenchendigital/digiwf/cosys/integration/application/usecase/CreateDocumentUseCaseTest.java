package io.muenchendigital.digiwf.cosys.integration.application.usecase;

import io.muenchendigital.digiwf.cosys.integration.adapter.out.ProcessAdapter;
import io.muenchendigital.digiwf.cosys.integration.application.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.cosys.integration.application.port.out.GenerateDocumentPort;
import io.muenchendigital.digiwf.cosys.integration.application.port.out.SaveFileToStoragePort;
import io.muenchendigital.digiwf.cosys.integration.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.ProcessApiImpl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.any;

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
    private Map<String,String> variables = Map.of("key1", "value");
    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", variables, listOfURls);

    @Test
    void createDocument() {
        when(generateDocumentPort.generateCosysDocument(any())).thenReturn(Mono.just("Document".getBytes()));

        final CreateDocumentUseCase useCase = new CreateDocumentUseCase(saveFileToStoragePort,correlateMessagePort, generateDocumentPort);
        useCase.createDocument("processInstanceIde", "messageName", generateDocument);

        verify(generateDocumentPort).generateCosysDocument(generateDocument);
        verifyNoMoreInteractions(generateDocumentPort);

        verify(saveFileToStoragePort).saveDocumentInStorage(generateDocument, "Document".getBytes());
        verifyNoMoreInteractions(saveFileToStoragePort);
    }

}