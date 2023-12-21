package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateIncidentInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CreateIncidentOutPort;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
class CreateIncidentUseCase implements CreateIncidentInPort {

    private final CreateIncidentOutPort outPort;

    @Override
    public void createIncident(@NotBlank String processInstanceId, @NotBlank String messageName, String messageContent) {
        log.info("Received create incident for process instance with id: {}", processInstanceId);
        outPort.createIncident(processInstanceId, messageName, messageContent);
    }
}
