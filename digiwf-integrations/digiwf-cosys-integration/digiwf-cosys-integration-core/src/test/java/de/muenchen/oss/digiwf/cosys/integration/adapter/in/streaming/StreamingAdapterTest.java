package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateDocumentInPort;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StreamingAdapterTest {

    private final ProcessApi processApiMock = mock(ProcessApi.class);
    private final ErrorApi errorApiMock = mock(ErrorApi.class);
    private final CreateDocumentInPort createDocumentMock = mock(CreateDocumentInPort.class);
    // dummy data
    private final String processInstanceId = "ProcessInstanceId";
    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_INTEGRATION_NAME, "integrationName", TYPE, "type"));
    private final List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private final GenerateDocumentPresignedUrlsDTO generateDocumentPresignedUrlsDTO = new GenerateDocumentPresignedUrlsDTO("Client", "Role", "guid", null, listOfURls);
    private final GenerateDocumentDTO generateDocumentDTO = new GenerateDocumentDTO("Client", "Role", "guid", null, "fileContext", "filePath.txt");
    Message<GenerateDocumentPresignedUrlsDTO> messagePresignedUrls;
    Message<GenerateDocumentDTO> message;
    private StreamingAdapter streamingAdapter;

    @BeforeEach
    void setup() {
        this.streamingAdapter = new StreamingAdapter(createDocumentMock, processApiMock, errorApiMock);
        this.messagePresignedUrls = new Message<>() {
            @Override
            public GenerateDocumentPresignedUrlsDTO getPayload() {
                return generateDocumentPresignedUrlsDTO;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
        this.message = new Message<>() {
            @Override
            public GenerateDocumentDTO getPayload() {
                return generateDocumentDTO;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void cosysIntegrationCreateDocumentSuccessfully() {
        // deprecated
        streamingAdapter.createCosysDocument().accept(this.messagePresignedUrls);
        verify(createDocumentMock).createDocument(generateDocumentPresignedUrlsDTO, generateDocumentPresignedUrlsDTO.getDocumentStorageUrls());
        // v2
        streamingAdapter.createCosysDocumentV2().accept(this.message);
        verify(createDocumentMock).createDocument(generateDocumentDTO, generateDocumentDTO.getFileContext(), generateDocumentDTO.getFilePath());
        verifyNoMoreInteractions(createDocumentMock);
    }

    @Test
    void cosysIntegrationHandlesValidationException() {
        doThrow(new ValidationException("ValidationException")).when(createDocumentMock).createDocument(any(), any());
        // deprecated
        streamingAdapter.createCosysDocument().accept(this.messagePresignedUrls);
        ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        // v2
        streamingAdapter.createCosysDocumentV2().accept(this.message);
        messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        verifyNoMoreInteractions(errorApiMock);
    }

    @Test
    void cosysIntegrationHandlesBpmnError() {
        doThrow(new BpmnError("S3_FILE_SAVE_ERROR", "BpmnErrorCode")).when(createDocumentMock).createDocument(any(), any());
        // deprecated
        streamingAdapter.createCosysDocument().accept(this.messagePresignedUrls);
        ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        // v2
        streamingAdapter.createCosysDocumentV2().accept(this.message);
        messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        verifyNoMoreInteractions(errorApiMock);
    }

    @Test
    void cosysIntegrationIncidentError() {
        doThrow(new IncidentError("IncidentError")).when(createDocumentMock).createDocument(any(), any());
        // deprecated
        streamingAdapter.createCosysDocument().accept(this.messagePresignedUrls);
        ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        // v2
        streamingAdapter.createCosysDocumentV2().accept(this.message);
        messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        assertTrue(messageHeaderArgumentCaptor.getValue().containsKey(DIGIWF_PROCESS_INSTANCE_ID));
        verifyNoMoreInteractions(errorApiMock);
    }

}
