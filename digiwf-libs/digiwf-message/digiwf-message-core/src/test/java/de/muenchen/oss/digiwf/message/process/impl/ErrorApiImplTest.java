package de.muenchen.oss.digiwf.message.process.impl;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.message.process.impl.dto.BpmnErrorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ErrorApiImplTest {

    private final MessageApi messageApi = Mockito.spy(Mockito.mock(MessageApi.class));

    private final ErrorApi errorApi = new ErrorApiImpl(
            this.messageApi,
            this.incidentDestination,
            this.bpmnErrorDestination
    );

    // dummy data
    private final String processInstanceId = "processInstanceId-123";
    private final String incidentDestination = "incidentMessageDestination";
    private final String bpmnErrorDestination = "bpmnErrorMessageDestination";
    private final String messageName = "someMessageName";
    private final Map<String, Object> messageHeaders = Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId,
            DIGIWF_MESSAGE_NAME, this.messageName);


    @BeforeEach
    void setUp() {
        when(this.messageApi.sendMessage(any(), anyMap(), anyString())).thenReturn(true);
    }

    @Test
    void testHandleIncident() {
        final boolean success = this.errorApi.handleIncident(this.processInstanceId, this.messageName, "someErrorMessage");
        assertThat(success).isTrue();
        this.verifyIncidentMessageApiCall("someErrorMessage", this.incidentDestination, this.messageName, this.incidentDestination);
    }

    @Test
    void testHandleBpmnError() {
        final String errorCode = "400";
        final String errorMessage = "someErrorMessage";
        final boolean success = this.errorApi.handleBpmnError(this.processInstanceId, errorCode, errorMessage);
        assertThat(success).isTrue();
        this.verifyBpmnErrorMessageApiCall(new BpmnError(errorCode, errorMessage), this.bpmnErrorDestination);
    }

    @Test
    void testHandleBpmnErrorWithException() {
        final BpmnError bpmnError = new BpmnError("400", "someErrorMessage");
        final boolean success = this.errorApi.handleBpmnError(this.messageHeaders, bpmnError);
        assertThat(success).isTrue();
        this.verifyBpmnErrorMessageApiCall(bpmnError, this.bpmnErrorDestination);
    }

    @Test
    void testHandleBpmnErrorWithExceptionRaisesRuntimeExceptionOnMissingProcessInstance() {
        final BpmnError bpmnError = new BpmnError("400", "someErrorMessage");
        assertThatThrownBy(() -> this.errorApi.handleBpmnError(Map.of(DIGIWF_MESSAGE_NAME, "someMessage"), bpmnError))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testHandleIncidentWithException() {
        final IncidentError incidentError = new IncidentError("someErrorMessage");
        final boolean success = this.errorApi.handleIncident(this.messageHeaders, incidentError);
        assertThat(success).isTrue();
        this.verifyIncidentMessageApiCall("someErrorMessage", this.incidentDestination, this.messageName, this.incidentDestination);
    }

    @Test
    void testHandleIncidentWithExceptionRaisesRuntimeExceptionOnMissingProcessInstance() {
        final IncidentError incidentError = new IncidentError("someErrorMessage");
        assertThatThrownBy(() -> this.errorApi.handleIncident(Map.of(DIGIWF_MESSAGE_NAME, "someMessage"), incidentError))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testHandleIncidentWithExceptionRaisesRuntimeExceptionOnMissingMessageName() {
        final IncidentError incidentError = new IncidentError("someErrorMessage");
        assertThatThrownBy(() -> this.errorApi.handleIncident(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId), incidentError))
                .isInstanceOf(RuntimeException.class);
    }

    private void verifyBpmnErrorMessageApiCall(final BpmnError payload, final String destination) {
        final ArgumentCaptor<BpmnErrorDto> payloadCaptor = ArgumentCaptor.forClass(BpmnErrorDto.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi, times(2)).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        Assertions.assertEquals(payload.getErrorMessage(), payloadCaptor.getValue().getErrorMessage());
        Assertions.assertEquals(payload.getErrorCode(), payloadCaptor.getValue().getErrorCode());
        Assertions.assertEquals(this.processInstanceId, payloadCaptor.getValue().getProcessInstanceId());
        Assertions.assertEquals(destination, destinationCaptor.getValue());
        Assertions.assertEquals("bpmnError", headersCaptor.getValue().get(DIGIWF_MESSAGE_NAME), "Message name should be bpmnError");
        Assertions.assertEquals("bpmnerror", headersCaptor.getValue().get(TYPE), "Message type should be bpmnerror");
    }

    private void verifyIncidentMessageApiCall(final String payload, final String typeHeader, final String messageNameHeader, final String destination) {
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, Object>> headersCaptor = ArgumentCaptor.forClass(Map.class);
        final ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.messageApi, times(2)).sendMessage(payloadCaptor.capture(), headersCaptor.capture(), destinationCaptor.capture());

        assertThat(payloadCaptor.getValue()).isEqualTo(payload);

        assertThat(headersCaptor.getValue())
                .hasSize(3)
                .containsEntry(TYPE, typeHeader)
                .containsEntry(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId)
                .containsEntry(DIGIWF_MESSAGE_NAME, messageNameHeader);

        assertThat(destinationCaptor.getValue()).isEqualTo(destination);
    }

}
