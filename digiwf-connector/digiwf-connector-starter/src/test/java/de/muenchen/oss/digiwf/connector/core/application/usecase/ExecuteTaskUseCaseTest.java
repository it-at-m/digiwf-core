package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.DigiWFConnectorProperties;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort.ExecuteTaskCommand;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;

class ExecuteTaskUseCaseTest {

    private ExecuteTaskUseCase useCase;
    private EmitEventOutPort emitEventOutPort;
    private DigiWFConnectorProperties digiWFConnectorProperties;

    @BeforeEach
    void setUp() {
        emitEventOutPort = mock(EmitEventOutPort.class);
        digiWFConnectorProperties = mock(DigiWFConnectorProperties.class);
        useCase = new ExecuteTaskUseCase(emitEventOutPort, digiWFConnectorProperties);
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndDefaultDestination() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setMessageName(StringUtils.EMPTY);
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of("testIntegrationName", "defaultDestination"));

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getMessageName(), "defaultDestination", command.getType(), command.getInstanceId(), command.getData());
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndCustomDestination() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setMessageName(StringUtils.EMPTY);
        command.setCustomDestination("customDestination");
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of("testIntegrationName", "defaultDestination"));

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getMessageName(), command.getCustomDestination(), command.getType(), command.getInstanceId(), command.getData());
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersAndCustomDestinationForCustomIntegration() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setMessageName(StringUtils.EMPTY);
        command.setCustomDestination("customDestination");
        command.setIntegrationName("testIntegrationName");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());
        when(digiWFConnectorProperties.getIntegrations()).thenReturn(Map.of());

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getMessageName(), command.getCustomDestination(), command.getType(), command.getInstanceId(), command.getData());
    }
}
