package de.muenchen.oss.digiwf.email.integration.application.usecase;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailPort;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.email.integration.model.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SendMailUseCaseTest {

    private final LoadMailAttachmentPort loadMailAttachmentPort = mock(LoadMailAttachmentPort.class);
    private final CorrelateMessagePort correlateMessagePort = mock(CorrelateMessagePort.class);
    private final MailPort mailPort = mock(MailPort.class);

    private SendMail sendMail;

    private final Mail mail = new Mail(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "This is a test mail",
            "digiwf@muenchen.de",
            null
    );
    private final String processInstanceId = "processInstanceId";
    private final String messageName = "messageName";

    @BeforeEach
    void setUp() {
        this.sendMail = new SendMailUseCase(loadMailAttachmentPort, correlateMessagePort, mailPort);
    }

    @Test
    void sendMail() throws MessagingException {
        sendMail.sendMail(processInstanceId, messageName, mail);
        final de.muenchen.oss.digiwf.email.model.Mail mailOutModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .body(mail.getBody())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(List.of())
                .build();
        verify(mailPort).sendMail(mailOutModel);
        verify(correlateMessagePort).correlateMessage(processInstanceId, messageName, Map.of("mailSentStatus", true));
    }

    @Test
    void sendMailWithAttachments() throws MessagingException {
        final PresignedUrl presignedUrl = new PresignedUrl("http://localhost:9000/some-url", "test.txt", "GET");
        mail.setAttachments(List.of(presignedUrl));

        final FileAttachment fileAttachment = new FileAttachment("test.txt", new ByteArrayDataSource("Anhang Inhalt".getBytes(), "text/plain"));
        when(loadMailAttachmentPort.loadAttachment(presignedUrl)).thenReturn(fileAttachment);

        sendMail.sendMail(processInstanceId, messageName, mail);
        final de.muenchen.oss.digiwf.email.model.Mail mailOutModel = de.muenchen.oss.digiwf.email.model.Mail.builder()
                .receivers(mail.getReceivers())
                .subject(mail.getSubject())
                .body(mail.getBody())
                .replyTo(mail.getReplyTo())
                .receiversCc(mail.getReceiversCc())
                .receiversBcc(mail.getReceiversBcc())
                .attachments(List.of(fileAttachment))
                .build();
        verify(mailPort).sendMail(mailOutModel);
        verify(correlateMessagePort).correlateMessage(processInstanceId, messageName, Map.of("mailSentStatus", true));
    }

    @Test
    void sendMailThrowsBpmnError() throws MessagingException {
        doThrow(new MessagingException("Test Exception")).when(mailPort).sendMail(any());
        assertThatThrownBy(() -> sendMail.sendMail(processInstanceId, messageName, mail)).isInstanceOf(BpmnError.class);
    }
}
