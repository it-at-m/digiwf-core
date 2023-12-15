package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.out.CreateIncidentOutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateIncidentUseCaseTest {

    private CreateIncidentUseCase useCase;
    private CreateIncidentOutPort outPort;

    @BeforeEach
    void setUp() {
        outPort = mock(CreateIncidentOutPort.class);
        useCase = new CreateIncidentUseCase(outPort);
    }

    @Test
    void createIncident_shouldCallOutPortWithSameArguments() {
        // Arrange
        String processInstanceId = "123";
        String messageName = "testMessage";
        String messageContent = "Test Content";

        // Act
        useCase.createIncident(processInstanceId, messageName, messageContent);

        // Assert
        verify(outPort).createIncident(processInstanceId, messageName, messageContent);
    }
}
