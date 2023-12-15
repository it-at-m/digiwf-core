package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CorrelateMessageUseCaseTest {

    private CorrelateMessageUseCase useCase;
    private CorrelateMessageOutPort correlateMessageOutPort;

    @BeforeEach
    void setUp() {
        correlateMessageOutPort = mock(CorrelateMessageOutPort.class);
        useCase = new CorrelateMessageUseCase(correlateMessageOutPort);
    }

    @Test
    void correlateMessage_shouldCallOutPortWithSameMessageCorrelation() {
        // given
        MessageCorrelation messageCorrelation = new MessageCorrelation();
        messageCorrelation.setProcessInstanceId("123");
        messageCorrelation.setMessageName("testMessage");

        // when
        useCase.correlateMessage(messageCorrelation);

        // then
        verify(correlateMessageOutPort).correlateMessage(messageCorrelation);
    }
}
