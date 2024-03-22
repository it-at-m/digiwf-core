package de.muenchen.oss.digiwf.example.integration.core.application.usecase;

import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ProcessResponseInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ProcessResponseOutPort;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProcessResponseUseCase implements ProcessResponseInPort {
    private final ProcessResponseOutPort processResponseOutPort;


    @Override
    public void correlateMessage(final Map<String, Object> originMessageHeaders, final Map<String, Object> message) {
        processResponseOutPort.correlateMessage(originMessageHeaders, message);
    }

    @Override
    public boolean handleBpmnError(final Map<String, Object> originMessageHeaders, final BpmnError bpmnError) {
        return processResponseOutPort.handleBpmnError(originMessageHeaders, bpmnError);
    }

    @Override
    public boolean handleIncident(final Map<String, Object> originMessageHeaders, final IncidentError incidentError) {
        return processResponseOutPort.handleIncident(originMessageHeaders, incidentError);
    }
}
