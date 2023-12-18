package de.muenchen.oss.digiwf.email.integration.adapter.in;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageProcessorTest {
    private final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    private final SendMail sendMailMock = Mockito.mock(SendMail.class);
    private final MonitoringService monitoringServiceMock = Mockito.mock(MonitoringService.class);

    private MessageProcessor messageProcessor;
    private Message<Mail> message;

    // dummy data
    private final String processInstanceId = "exampleProcessInstanceId";
    private final Mail mail = new Mail(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "This is a test mail",
            "digiwf@muenchen.de",
            null
    );
    private final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_MESSAGE_NAME, "messageName"));

    @BeforeEach
    void setup() {
        this.messageProcessor = new MessageProcessor(errorApiMock, sendMailMock, monitoringServiceMock);
        this.message = new Message<Mail>() {
            @Override
            public Mail getPayload() {
                return mail;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testEmailIntegrationSendsMailSuccessfully() {
        messageProcessor.emailIntegration().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailSucceeded();
        verify(sendMailMock, times(1)).sendMail(processInstanceId, "messageName", mail);
    }

    @Test
    void testEmailIntegrationHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(sendMailMock).sendMail(any(), any(), any());
        messageProcessor.emailIntegration().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertThat(messageHeaderArgumentCaptor.getValue()).containsKey(DIGIWF_PROCESS_INSTANCE_ID);
    }

    @Test
    void testEmailIntegrationHandlesBpmnError() {
        Mockito.doThrow(new BpmnError("errorCode", "errorMessage")).when(sendMailMock).sendMail(any(), any(), any());
        messageProcessor.emailIntegration().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertThat(messageHeaderArgumentCaptor.getValue()).containsKey(DIGIWF_PROCESS_INSTANCE_ID);
    }

    @Test
    void testEmailIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(sendMailMock).sendMail(any(), any(), any());
        messageProcessor.emailIntegration().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        assertThat(messageHeaderArgumentCaptor.getValue()).containsKey(DIGIWF_PROCESS_INSTANCE_ID);
    }
}

