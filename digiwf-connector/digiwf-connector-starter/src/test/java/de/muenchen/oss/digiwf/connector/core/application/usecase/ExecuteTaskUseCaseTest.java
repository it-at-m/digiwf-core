package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.DigiWFConnectorProperties;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort.ExecuteTaskCommand;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.ProcessOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.IntegrationNameConfigException;
import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ExecuteTaskUseCaseTest {

    private ExecuteTaskUseCase useCase;
    private EmitEventOutPort emitEventOutPort;
    private ProcessOutPort processOutPort;
    private DigiWFConnectorProperties digiWFConnectorProperties;

    @BeforeEach
    void setUp() {
        emitEventOutPort = mock(EmitEventOutPort.class);
        digiWFConnectorProperties = mock(DigiWFConnectorProperties.class);
        processOutPort = mock(ProcessOutPort.class);
        useCase = new ExecuteTaskUseCase(digiWFConnectorProperties, emitEventOutPort, processOutPort);
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndDefaultDestination() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of("testIntegrationName", "defaultDestination"));
        when(processOutPort.loadProcessDefinition(command.getInstanceId())).thenReturn("processDefinition");

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent("defaultDestination", command.getType(), command.getIntegrationName(), command.getInstanceId(), "processDefinition", command.getData());
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndCustomDestination() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setCustomDestination("customDestination");
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of("testIntegrationName", "defaultDestination"));
        when(processOutPort.loadProcessDefinition(command.getInstanceId())).thenReturn("processDefinition");

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getCustomDestination(), command.getType(), command.getIntegrationName(), command.getInstanceId(), "processDefinition", command.getData());
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndCustomDestinationForCustomIntegration() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setCustomDestination("customDestination");
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of());
        when(processOutPort.loadProcessDefinition(command.getInstanceId())).thenReturn("processDefinition");

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getCustomDestination(), command.getType(), command.getIntegrationName(), command.getInstanceId(), "processDefinition", command.getData());
    }

    @Test
    void executeTask_shouldThrowIntegrationNameConfigExceptionIfIntegrationNameIsNotConfigured() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of());
        when(processOutPort.loadProcessDefinition(command.getInstanceId())).thenReturn("processDefinition");

        // Assert
        assertThatThrownBy(() -> useCase.executeTask(command))
                .isInstanceOf(IntegrationNameConfigException.class)
                .hasMessage("Integration testIntegrationName is not registered in configuration.")
                .hasFieldOrPropertyWithValue("detailedMessage", "Integration testIntegrationName is not registered in configuration. Make sure to register it in configuration or provide a custom topic.");
    }

    @Test
    void executeTask_shouldThrowProcessDefinitionLoadingExceptionIfProcessDefinitionCouldNotBeLoaded() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of("testIntegrationName", "defaultDestination"));
        when(processOutPort.loadProcessDefinition(command.getInstanceId())).thenThrow(new ProcessDefinitionLoadingException("testException", "testDetailedMessage"));

        // Assert
        assertThatThrownBy(() -> useCase.executeTask(command))
                .isInstanceOf(ProcessDefinitionLoadingException.class)
                .hasMessage("testException")
                .hasFieldOrPropertyWithValue("detailedMessage", "testDetailedMessage");
    }
}
