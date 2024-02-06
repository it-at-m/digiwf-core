package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.processdefinition;

import de.muenchen.oss.digiwf.connector.core.application.port.out.ProcessOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessDefinitionAdapterTest {

    private final ProcessInstanceClient processInstanceClient = mock(ProcessInstanceClient.class);
    private ProcessOutPort processOutPort;

    @BeforeEach
    void setup() {
        processOutPort = new ProcessDefinitionAdapter(processInstanceClient);
    }


    @Test
    void test_processDefinitionAdapterReturnsRootProcessDefinitionForInstanceId() {
        // Arrange
        final String processInstanceId = "validProcessInstanceId";
        final String expectedDefinitionName = "rootProcessDefinition";

        final ServiceInstanceTO serviceInstanceTO = ServiceInstanceTO.builder()
                .id(processInstanceId)
                .definitionName(expectedDefinitionName)
                .build();

        when(processInstanceClient.getRootProcessInstanceDetail(processInstanceId)).thenReturn(serviceInstanceTO);

        // Act
        final String actualDefinitionName = processOutPort.loadProcessDefinition(processInstanceId);

        // Assert
        assertThat(actualDefinitionName).isEqualTo(expectedDefinitionName);
    }

    @Test
    void test_processDefinitionAdapterThrowsProcessDefinitionExceptionIfClientRequestFails() {
        // Arrange
        String processInstanceId = "validProcessInstanceId";
        String feignExceptionMessage = "Feign exception message";

        final FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn(feignExceptionMessage);

        when(processInstanceClient.getRootProcessInstanceDetail(processInstanceId)).thenThrow(feignException);

        // Act and Assert
        assertThatThrownBy(() -> processOutPort.loadProcessDefinition(processInstanceId))
                .isInstanceOf(ProcessDefinitionLoadingException.class)
                .hasMessage("Could not load process definition for process instance with id " + processInstanceId)
                .hasFieldOrPropertyWithValue("detailedMessage", feignExceptionMessage);
    }

}
