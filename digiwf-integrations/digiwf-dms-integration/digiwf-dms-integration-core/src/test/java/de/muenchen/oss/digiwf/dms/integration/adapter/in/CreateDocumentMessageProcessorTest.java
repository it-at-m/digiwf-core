package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.validation.ValidationException;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateDocumentMessageProcessorTest extends MessageProcessorTestBase {

    private final CreateDocumentDto createDocumentDto = new CreateDocumentDto(
            "documentCoo",
            "title",
            "user",
            "EINGEHEND",
            "filepaths",
            "filecontext"
    );
    private Message<CreateDocumentDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(createDocumentUseCaseMock.createDocument(
                        createDocumentDto.getProcedureCoo(),
                        createDocumentDto.getTitle(),
                        createDocumentDto.getUser(),
                        DocumentType.valueOf(createDocumentDto.getType()),
                        createDocumentDto.getFilepathsAsList(),
                        createDocumentDto.getFileContext()))
                .thenReturn("documentCOO");

        this.message = new Message<>() {

            @Override
            public CreateDocumentDto getPayload() {
                return createDocumentDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationCreateDocumentSuccessfully() {
        messageProcessor.createDocument().accept(this.message);
        verify(createDocumentUseCaseMock, times(1)).createDocument(
                createDocumentDto.getProcedureCoo(),
                createDocumentDto.getTitle(),
                createDocumentDto.getUser(),
                DocumentType.valueOf(createDocumentDto.getType()),
                createDocumentDto.getFilepathsAsList(),
                createDocumentDto.getFileContext());
    }

    @Test
    void testDmsIntegrationCreateDocumentHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(createDocumentUseCaseMock).createDocument(any(), any(), any(),any(),any(), any());
        messageProcessor.createDocument().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testDmsCreateDocumentIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(createDocumentUseCaseMock).createDocument(any(), any(), any(),any(),any(), any());
        messageProcessor.createDocument().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

