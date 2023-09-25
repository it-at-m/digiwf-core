package de.muenchen.oss.digiwf.cosys.integration.adapter.in;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocument;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.cosys.integration.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageProcessorTest {

    private final ErrorApi errorApiMock = mock(ErrorApi.class);
    private final CreateDocument createDocumentMock = mock(CreateDocument.class);

    private MessageProcessor messageProcessor;
    Message<GenerateDocument> message;

    // dummy data
    private final String processInstanceId = "ProcessInstanceId";
    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", null, listOfURls);

    private final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_MESSAGE_NAME, "messageName"));

    @BeforeEach
    void setup() {
        this.messageProcessor = new MessageProcessor(createDocumentMock,errorApiMock);
        this.message = new Message<GenerateDocument>() {
            @Override
            public GenerateDocument getPayload() {
                return generateDocument;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void cosysIntegrationCreateDocumentSuccessfully() {
        messageProcessor.cosysIntegration().accept(this.message);
        verify(createDocumentMock).createDocument(processInstanceId,"messageName", generateDocument);
        verifyNoMoreInteractions(createDocumentMock);
    }

    @Test
    void cosysIntegrationHandlesValidationException() {
        doThrow(new ValidationException("ValidationException")).when(createDocumentMock).createDocument(any(),any(),any());
        messageProcessor.cosysIntegration().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        verifyNoMoreInteractions(errorApiMock);
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }

    @Test
    void cosysIntegrationHandlesBpmnError() {
        doThrow(new BpmnError("S3_FILE_SAVE_ERROR","BpmnErrorCode")).when(createDocumentMock).createDocument(any(),any(),any());
        messageProcessor.cosysIntegration().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        verifyNoMoreInteractions(errorApiMock);
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }

    @Test
    void cosysIntegrationIncidentError() {
        doThrow(new IncidentError("IncidentError")).when(createDocumentMock).createDocument(any(),any(),any());
        messageProcessor.cosysIntegration().accept(this.message);
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        verifyNoMoreInteractions(errorApiMock);
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
    }

}
