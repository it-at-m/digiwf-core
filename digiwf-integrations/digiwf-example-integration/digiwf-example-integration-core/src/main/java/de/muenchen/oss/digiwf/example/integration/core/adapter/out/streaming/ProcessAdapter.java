package de.muenchen.oss.digiwf.example.integration.core.adapter.out.streaming;

import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ProcessResponseOutPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessAdapter implements ProcessResponseOutPort {
    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    @Override
    public void correlateMessage(Map<String, Object> originMessageHeaders, Map<String, Object> message) {
        String processInstanceId = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID).toString());
        String type = Objects.requireNonNull(originMessageHeaders.get(TYPE).toString());
        String integrationName = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_INTEGRATION_NAME).toString());
        log.info("sending response message for process {}: {}", processInstanceId, message);
        this.processApi.correlateMessage(processInstanceId, type, integrationName, message);
    }

    @Override
    public boolean handleBpmnError(Map<String, Object> originMessageHeaders, BpmnError bpmnError) {
        return errorApi.handleBpmnError(originMessageHeaders, bpmnError);
    }

    @Override
    public boolean handleIncident(Map<String, Object> originMessageHeaders, IncidentError incidentError) {
        return errorApi.handleIncident(originMessageHeaders, incidentError);
    }
}
