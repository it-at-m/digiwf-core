package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort.ExecuteTaskCommand;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExecuteTaskUseCaseTest {

    private ExecuteTaskUseCase useCase;
    private EmitEventOutPort emitEventOutPort;

    @BeforeEach
    void setUp() {
        emitEventOutPort = mock(EmitEventOutPort.class);
        useCase = new ExecuteTaskUseCase(emitEventOutPort);
    }

    @Test
    void executeTask_shouldCallEmitEventWithCorrectParametersWhenMessageNameIsNotBlank() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setMessageName("testMessageName");
        command.setDestination("testDestination");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getMessageName(), command.getDestination(), command.getType(), command.getInstanceId(), command.getData());
    }

    @Test
    void executeTask_shouldCallEmitEventWithDifferentParametersWhenMessageNameIsBlank() {
        // Arrange
        ExecuteTaskCommand command = new ExecuteTaskCommand();
        command.setMessageName(StringUtils.EMPTY); // Explicitly set message name to blank
        command.setDestination("testDestination");
        command.setType("testType");
        command.setInstanceId("123");
        command.setData(Map.of());

        // Act
        useCase.executeTask(command);

        // Assert
        verify(emitEventOutPort).emitEvent(command.getDestination(), command.getType(), command.getInstanceId(), command.getData());
    }
}
