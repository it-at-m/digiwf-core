package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.dms.integration.domain.DocumentResponse;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UpdateDocumentStreamingAdapterTest extends StreamingAdapterTestBase {

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
        Mockito.doReturn(new DocumentResponse("documentCoo", List.of("contentCoo1"))).when(updateDocumentInPortMock).updateDocument(
                updateDocumentDto.getDocumentCoo(),
                updateDocumentDto.getUser(),
                DocumentType.valueOf(updateDocumentDto.getType()),
                updateDocumentDto.getFilepathsAsList(),
                updateDocumentDto.getFileContext(),
                processDefinitionId);

        this.message = new Message<>() {

            @NotNull
            @Override
            public UpdateDocumentDto getPayload() {
                return updateDocumentDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationUpdateDocumentSuccessfully() {
        streamingAdapter.updateDocument().accept(this.message);
        verify(updateDocumentInPortMock, times(1)).updateDocument(
                updateDocumentDto.getDocumentCoo(),
                updateDocumentDto.getUser(),
                DocumentType.valueOf(updateDocumentDto.getType()),
                updateDocumentDto.getFilepathsAsList(),
                updateDocumentDto.getFileContext(),
                processDefinitionId);
    }

    @Test
    void testDmsIntegrationUpdateDocumentHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(updateDocumentInPortMock)
                .updateDocument(any(), any(), any(), any(), any(), any());
        streamingAdapter.updateDocument().accept(this.message);
        final ArgumentCaptor<Map<String, Object>> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }

    @Test
    void testDmsUpdateDocumentIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(updateDocumentInPortMock).updateDocument(any(), any(), any(), any(), any(), any());
        streamingAdapter.updateDocument().accept(this.message);
        final ArgumentCaptor<Map<String, Object>> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        Assertions.assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }
}

