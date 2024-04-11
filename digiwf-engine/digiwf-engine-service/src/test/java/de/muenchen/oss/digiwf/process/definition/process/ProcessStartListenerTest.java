package de.muenchen.oss.digiwf.process.definition.process;

import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceInstanceInitializationService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessStartListenerTest {

    @Mock
    private RepositoryService repositoryService;

    @Mock
    private ServiceInstanceInitializationService initializationService;

    @Mock
    private DelegateExecution execution;

    @Mock
    private ProcessDefinition processDefinition;

    @Mock
    private FlowElement bpmnModelElementInstance;

    @InjectMocks
    private ProcessStartListener processStartListener;

    @Test
    void testInitialize() {

        //given
        when(execution.getBpmnModelElementInstance()).thenReturn(bpmnModelElementInstance);
        when(bpmnModelElementInstance.getElementType()).thenReturn(mock(ModelElementType.class));
        when(bpmnModelElementInstance.getElementType().getTypeName()).thenReturn("startEvent");
        when(execution.getProcessInstanceId()).thenReturn("processInstanceId");
        when(execution.getId()).thenReturn("processInstanceId");
        when(execution.getProcessInstance()).thenReturn(execution);
        when(execution.getSuperExecution()).thenReturn(null);
        when(execution.getProcessDefinitionId()).thenReturn("processDefinitionId");
        when(repositoryService.getProcessDefinition(anyString())).thenReturn(processDefinition);
        when(processDefinition.getKey()).thenReturn("processDefinitionKey");

        // then
        processStartListener.startProcessInstance(execution);

        // then
        verify(initializationService).initializeInstance(
                "processInstanceId",
                "processDefinitionKey",
                execution.getVariables()
        );
    }

    @Test
    void testWithSuperExecution() {
        when(execution.getBpmnModelElementInstance()).thenReturn(bpmnModelElementInstance);
        when(bpmnModelElementInstance.getElementType()).thenReturn(mock(ModelElementType.class));
        when(bpmnModelElementInstance.getElementType().getTypeName()).thenReturn("startEvent");
        when(execution.getProcessInstanceId()).thenReturn("processInstanceId");
        when(execution.getId()).thenReturn("processInstanceId");
        when(execution.getProcessInstance()).thenReturn(execution);
        when(execution.getSuperExecution()).thenReturn(execution);

        // Execute the method to be tested
        processStartListener.startProcessInstance(execution);

        //Verify no interactions
        verifyNoInteractions(initializationService);
    }

    @Test
    void testWithNestedExecution() {
        when(execution.getBpmnModelElementInstance()).thenReturn(bpmnModelElementInstance);
        when(bpmnModelElementInstance.getElementType()).thenReturn(mock(ModelElementType.class));
        when(bpmnModelElementInstance.getElementType().getTypeName()).thenReturn("startEvent");
        when(execution.getProcessInstanceId()).thenReturn("processInstanceId");
        when(execution.getId()).thenReturn("processInstanceIdNested");

        // Execute the method to be tested
        processStartListener.startProcessInstance(execution);

        //Verify no interactions
        verifyNoInteractions(initializationService);
    }

    @Test
    void testWithEndEvent() {
        when(execution.getBpmnModelElementInstance()).thenReturn(bpmnModelElementInstance);
        when(bpmnModelElementInstance.getElementType()).thenReturn(mock(ModelElementType.class));
        when(bpmnModelElementInstance.getElementType().getTypeName()).thenReturn("endEvent");

        // Execute the method to be tested
        processStartListener.startProcessInstance(execution);

        //Verify no interactions
        verifyNoInteractions(initializationService);
    }
}