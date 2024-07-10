package de.muenchen.oss.digiwf.email.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TemplateMailPresigned;
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

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SendMailWithLogoAndLinkStreamingAdapterTest extends StreamingAdapterTestBase {

    private final MailWithLogoAndLinkPresignedDto mailWithLogoAndLinkDto = new MailWithLogoAndLinkPresignedDto(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "digiwf@muenchen.de",
            null,
            "template",
            "text",
            "bottomBody",
            "buttonText",
            "buttonLink"
    );

    private Message<MailWithLogoAndLinkPresignedDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        this.message = new Message<MailWithLogoAndLinkPresignedDto>() {
            @Override
            public MailWithLogoAndLinkPresignedDto getPayload() {
                return mailWithLogoAndLinkDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testEmailIntegrationSendsMailSuccessfully() {
        TemplateMailPresigned templateMail = new TemplateMailPresigned(
                "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
                "receiverCC@muenchen.de",
                "receiverBCC@muenchen.de",
                "Test Mail",
                "digiwf@muenchen.de",
                null,
                "template",
                Map.of("mail", mailWithLogoAndLinkDto)
        );
        streamingAdapter.sendMailWithLogoAndLink().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailSucceeded();
        verify(sendMailPresignedInPortMock, times(1)).sendMailWithTemplate(templateMail);
    }

    @Test
    void testEmailIntegrationHandlesValidationException() {
        Mockito.doThrow(new ValidationException("Test ValidationException")).when(sendMailPresignedInPortMock).sendMailWithTemplate(any());
        streamingAdapter.sendMailWithLogoAndLink().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertThat(messageHeaderArgumentCaptor.getValue())
                .containsKey(DIGIWF_PROCESS_INSTANCE_ID)
                .containsKey(DIGIWF_INTEGRATION_NAME)
                .containsKey(TYPE);
    }

    @Test
    void testEmailIntegrationHandlesBpmnError() {
        Mockito.doThrow(new BpmnError("errorCode", "errorMessage")).when(sendMailPresignedInPortMock).sendMailWithTemplate(any());
        streamingAdapter.sendMailWithLogoAndLink().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleBpmnError(messageHeaderArgumentCaptor.capture(), any(BpmnError.class));
        assertThat(messageHeaderArgumentCaptor.getValue())
                .containsKey(DIGIWF_PROCESS_INSTANCE_ID)
                .containsKey(DIGIWF_INTEGRATION_NAME)
                .containsKey(TYPE);
    }

    @Test
    void testEmailIntegrationHandlesIncidentError() {
        Mockito.doThrow(new IncidentError("Error Message")).when(sendMailPresignedInPortMock).sendMailWithTemplate(any());
        streamingAdapter.sendMailWithLogoAndLink().accept(this.message);
        verify(monitoringServiceMock, times(1)).sendMailFailed();
        final ArgumentCaptor<Map> messageHeaderArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(errorApiMock, times(1)).handleIncident(messageHeaderArgumentCaptor.capture(), any(IncidentError.class));
        assertThat(messageHeaderArgumentCaptor.getValue())
                .containsKey(DIGIWF_PROCESS_INSTANCE_ID)
                .containsKey(DIGIWF_INTEGRATION_NAME)
                .containsKey(TYPE);
    }
}

