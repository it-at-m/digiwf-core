package io.muenchendigital.digiwf.input.incident.domain.service;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.muenchendigital.digiwf.input.incident.domain.model.Incident;
import io.muenchendigital.digiwf.service.instance.process.ProcessConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateIncidentDelegate implements JavaDelegate {

    private final EngineIncidentService engineIncidentService;

    public static final VariableFactory<String> MESSAGE_NAME = stringVariable("messageName");
    public static final VariableFactory<String> ERROR_MESSAGE = stringVariable(ProcessConstants.PROCESS_ERROR_MESSAGE);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.debug("Creating incident");

        final Incident incident = Incident.builder()
                .messageName(MESSAGE_NAME.from(delegateExecution).get())
                .processInstanceId(delegateExecution.getProcessInstanceId())
                .errorMessage(ERROR_MESSAGE.from(delegateExecution).getOrDefault(""))
                .build();
        engineIncidentService.createIncident(incident);
    }
}
