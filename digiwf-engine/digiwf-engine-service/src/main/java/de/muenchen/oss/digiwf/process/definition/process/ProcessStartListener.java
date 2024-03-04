package de.muenchen.oss.digiwf.process.definition.process;

import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceInstanceInitializationService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessStartListener {

    private final RepositoryService repositoryService;
    private final ServiceInstanceInitializationService initializationService;

    @EventListener(condition = "#execution.eventName.equals('start')")
    public void startProcessInstance(DelegateExecution execution) {
        if (isModelElementOfType("startEvent", execution)
                && isProcessStartEvent(execution)
                && isRootProcessInstance(execution)) {
            initializationService.initializeInstance(
                    execution.getProcessInstanceId(),
                    getProcessDefinitionKey(execution),
                    execution.getVariables()
            );
        }
    }

    private String getProcessDefinitionKey(DelegateExecution execution) {
        return repositoryService.getProcessDefinition(execution.getProcessDefinitionId()).getKey();
    }

    private boolean isRootProcessInstance(DelegateExecution execution) {
        return execution.getProcessInstance().getSuperExecution() == null;
    }

    private boolean isProcessStartEvent(DelegateExecution execution) {
        return execution.getProcessInstanceId().equals(execution.getId()) && execution.getActivityInstanceId() == null;
    }

    private boolean isModelElementOfType(String typeName, DelegateExecution execution) {
        return execution.getBpmnModelElementInstance() != null
                && typeName.equals(execution.getBpmnModelElementInstance().getElementType().getTypeName());
    }

}
