package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateBpmnErrorInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CreateBpmnErrorOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
class CreateBpmnErrorUseCase implements CreateBpmnErrorInPort {

    private final CreateBpmnErrorOutPort createBpmnErrorOutPort;

    @Override
    public void createBpmnError(@Valid BpmnError bpmnError) {
        log.info("Received bpmn error {}", bpmnError);
        createBpmnErrorOutPort.createBpmnError(bpmnError);
    }
}
