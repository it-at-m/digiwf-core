package de.muenchen.oss.digiwf.email.integration.application.usecase;

import de.muenchen.oss.digiwf.email.integration.adapter.in.MailWithLogoAndLinkDto;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailOutPort;
import de.muenchen.oss.digiwf.email.integration.model.TextMail;
import de.muenchen.oss.digiwf.email.integration.model.TemplateMail;
import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SendMailUseCaseTest {

    private final LoadMailAttachmentOutPort loadMailAttachmentOutPort = mock(LoadMailAttachmentOutPort.class);
    private final CorrelateMessageOutPort correlateMessageOutPort = mock(CorrelateMessageOutPort.class);
    private final MailOutPort mailOutPort = mock(MailOutPort.class);

    private SendMailInPort sendMailInPort;

    private final TextMail mail = new TextMail(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "This is a test mail",
            "digiwf@muenchen.de",
            null
    );

    private final MailWithLogoAndLinkDto mailWithLogoAndLinkDto = new MailWithLogoAndLinkDto(
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

    private final TemplateMail templateMail = new TemplateMail(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "digiwf@muenchen.de",
            null,
            "template",
            Map.of("mail", mailWithLogoAndLinkDto)
    );

    private final String processInstanceId = "processInstanceId";
    private final String integrationName = "emailIntegration";
    private final String type = "type";

    @BeforeEach
    void setUp() {
        this.sendMailInPort = new SendMailUseCase(loadMailAttachmentOutPort, correlateMessageOutPort, mailOutPort);
    }

    @Test
    void sendMail() throws MessagingException {
        sendMailInPort.sendMailWithText(processInstanceId, type, integrationName, mail);
        final de.muenchen.oss.digiwf.email.model.Mail mailOutModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .body(mail.getBody())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(List.of())
                .build();
        verify(mailOutPort).sendMail(mailOutModel);
        verify(correlateMessageOutPort).correlateMessage(processInstanceId, type, integrationName, Map.of("mailSentStatus", true));
    }

    @Test
    void sendMailWithAttachments() throws MessagingException {
        final PresignedUrl presignedUrl = new PresignedUrl("http://localhost:9000/some-url", "test.txt", "GET");
        mail.setAttachments(List.of(presignedUrl));

        final FileAttachment fileAttachment = new FileAttachment("test.txt", new ByteArrayDataSource("Anhang Inhalt".getBytes(), "text/plain"));
        when(loadMailAttachmentOutPort.loadAttachment(presignedUrl)).thenReturn(fileAttachment);

        sendMailInPort.sendMailWithText(processInstanceId, type, integrationName, mail);
        final de.muenchen.oss.digiwf.email.model.Mail mailOutModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .body(mail.getBody())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(List.of(fileAttachment))
                .build();
        verify(mailOutPort).sendMail(mailOutModel);
        verify(correlateMessageOutPort).correlateMessage(processInstanceId, type, integrationName, Map.of("mailSentStatus", true));
    }

    @Test
    void sendMailThrowsBpmnError() throws MessagingException {
        doThrow(new MessagingException("Test Exception")).when(mailOutPort).sendMail(any());
        assertThatThrownBy(() -> sendMailInPort.sendMailWithText(processInstanceId, type, integrationName, mail)).isInstanceOf(BpmnError.class);
    }

    @Test
    void sendMailWithTemplate() throws MessagingException, TemplateException, IOException {
        when(mailOutPort.getBodyFromTemplate(anyString(), anyMap())).thenReturn("generated body");
        sendMailInPort.sendMailWithTemplate(processInstanceId, type, integrationName, templateMail);
        final de.muenchen.oss.digiwf.email.model.Mail mailOutModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .htmlBody(true)
                .body("generated body")
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(List.of())
                .build();
        verify(mailOutPort).sendMail(mailOutModel);
        verify(correlateMessageOutPort).correlateMessage(processInstanceId, type, integrationName, Map.of("mailSentStatus", true));
    }

    @Test
    void sendMailWithTemplateThrowsIOException() throws TemplateException, IOException {
        doThrow(new IOException("IO Exception")).when(mailOutPort).getBodyFromTemplate(anyString(), anyMap());
        BpmnError bpmnError = catchThrowableOfType(() -> sendMailInPort.sendMailWithTemplate(processInstanceId, type, integrationName, templateMail), BpmnError.class);

        String expectedMessage = "The template " + templateMail.getTemplate() + " could not be loaded";
        String actualMessage = bpmnError.getErrorMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

        assertThat(bpmnError.getErrorCode()).isEqualTo("LOAD_TEMPLATE_FAILED");
    }

    @Test
    void sendMailWithTemplateThrowsTemplateException() throws TemplateException, IOException {
        TemplateException templateException = mock(TemplateException.class);
        when(templateException.getMessage()).thenReturn("Template Exception Message");
        doThrow(templateException).when(mailOutPort).getBodyFromTemplate(anyString(), anyMap());
        BpmnError bpmnError = catchThrowableOfType(() -> sendMailInPort.sendMailWithTemplate(processInstanceId, type, integrationName, templateMail), BpmnError.class);

        String expectedMessage = "Template Exception Message";
        String actualMessage = bpmnError.getErrorMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

        assertThat(bpmnError.getErrorCode()).isEqualTo("TEMPLATE_MERGING_FAILED");
    }

}
