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

class UpdateDocumentMessageProcessorTest extends MessageProcessorTestBase {

    private final UpdateDocumentDto updateDocumentDto = new UpdateDocumentDto(
            "documentCoo",
            "user",
            "EINGEHEND",
            "filepaths",
            "filecontext"
    );
    private Message<UpdateDocumentDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.doNothing().when(updateDocumentUseCaseMock).updateDocument(
                updateDocumentDto.getDocumentCoo(),
                updateDocumentDto.getUser(),
                DocumentType.valueOf(updateDocumentDto.getType()),
                updateDocumentDto.getFilepathsAsList(),
                updateDocumentDto.getFileContext());


        this.message = new Message<>() {
            @Override
            public UpdateDocumentDto getPayload() {
                return updateDocumentDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationUpdateDocumentSuccessfully() {
        messageProcessor.updateDocument().accept(this.message);
        verify(updateDocumentUseCaseMock, times(1)).updateDocument(
                updateDocumentDto.getDocumentCoo(),
                updateDocumentDto.getUser(),
                DocumentType.valueOf(updateDocumentDto.getType()),
                updateDocumentDto.getFilepathsAsList(),
                updateDocumentDto.getFileContext());
    }

    @Test
    void testDmsIntegrationUpdateDocumentHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(updateDocumentUseCaseMock).updateDocument(any(), any(), any(),any(),any());
        messageProcessor.updateDocument().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }


    @Test
    void testDmsUpdateDocumentIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(updateDocumentUseCaseMock).updateDocument(any(), any(), any(),any(),any());
        messageProcessor.updateDocument().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

