package integration.adapter.out;

import de.muenchen.oss.digiwf.address.integration.adapter.out.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.address.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class IntegrationOutAdapterTest {

    private final ProcessApi processApi = mock(ProcessApi.class);
    private final ErrorApi errorApi = mock(ErrorApi.class);

    private final IntegrationOutPort integrationOutAdapter = new IntegrationOutAdapter(processApi, errorApi);

    private final MessageHeaders messageHeaders = new MessageHeaders(Map.of(
            DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId",
            DIGIWF_MESSAGE_NAME, "messageName"
    ));

    @Test
    void testCorrelateProcessMessage() {
        final Map<String, Object> payload = Map.of("foo", "bar");

        integrationOutAdapter.correlateProcessMessage(messageHeaders, payload);

        final ArgumentCaptor<String> processInstanceIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> messageNameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        verify(processApi).correlateMessage(processInstanceIdCaptor.capture(), messageNameCaptor.capture(), payloadCaptor.capture());

        assertThat(processInstanceIdCaptor.getValue()).isEqualTo(messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID));
        assertThat(messageNameCaptor.getValue()).isEqualTo(messageHeaders.get(DIGIWF_MESSAGE_NAME));
        assertThat(payloadCaptor.getValue()).isEqualTo(payload);
    }

    @Test
    void testHandleBPMNError() {
        final String errorCode = "errorCode";
        final String errorMessage = "errorMessage";
        final BpmnError bpmnError = new BpmnError(errorCode, errorMessage);

        integrationOutAdapter.handleBpmnError(messageHeaders, bpmnError);

        final ArgumentCaptor<MessageHeaders> messageHeadersCaptor = ArgumentCaptor.forClass(MessageHeaders.class);
        final ArgumentCaptor<BpmnError> bpmnErrorCaptor = ArgumentCaptor.forClass(BpmnError.class);
        verify(errorApi).handleBpmnError(messageHeadersCaptor.capture(), bpmnErrorCaptor.capture());

        assertThat(messageHeadersCaptor.getValue()).isEqualTo(messageHeaders);
        assertThat(bpmnErrorCaptor.getValue()).isEqualTo(bpmnError);
        assertThat(bpmnErrorCaptor.getValue().getErrorCode()).isEqualTo(errorCode);
    }

    @Test
    void testHandleIncident() {
        final String errorMessage = "errorMessage";
        final IncidentError incidentError = new IncidentError(errorMessage);

        integrationOutAdapter.handleIncident(messageHeaders, incidentError);

        final ArgumentCaptor<MessageHeaders> messageHeadersCaptor = ArgumentCaptor.forClass(MessageHeaders.class);
        final ArgumentCaptor<IncidentError> incidentErrorArgumentCaptor = ArgumentCaptor.forClass(IncidentError.class);
        verify(errorApi).handleIncident(messageHeadersCaptor.capture(), incidentErrorArgumentCaptor.capture());

        assertThat(messageHeadersCaptor.getValue()).isEqualTo(messageHeaders);
        assertThat(incidentErrorArgumentCaptor.getValue()).isEqualTo(incidentError);
    }

}
