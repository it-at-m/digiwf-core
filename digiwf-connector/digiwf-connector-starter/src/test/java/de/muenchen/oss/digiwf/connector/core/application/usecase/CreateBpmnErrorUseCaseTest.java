package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.out.CreateBpmnErrorOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateBpmnErrorUseCaseTest {

    private CreateBpmnErrorUseCase useCase;
    private CreateBpmnErrorOutPort createBpmnErrorOutPort;

    @BeforeEach
    void setUp() {
        createBpmnErrorOutPort = mock(CreateBpmnErrorOutPort.class);
        useCase = new CreateBpmnErrorUseCase(createBpmnErrorOutPort);
    }

    @Test
    void createBpmnError_shouldCallOutPortWithSameBpmnError() {
        // given
        BpmnError bpmnError = new BpmnError();
        bpmnError.setProcessInstanceId("123");
        bpmnError.setMessageName("testMessage");
        bpmnError.setErrorCode("404");
        bpmnError.setErrorMessage("Not found");

        // when
        useCase.createBpmnError(bpmnError);

        // then
        verify(createBpmnErrorOutPort).createBpmnError(bpmnError);
    }
}

