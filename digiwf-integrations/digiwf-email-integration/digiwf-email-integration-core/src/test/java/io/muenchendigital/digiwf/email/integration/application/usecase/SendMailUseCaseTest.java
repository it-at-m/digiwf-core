package io.muenchendigital.digiwf.email.integration.application.usecase;

import io.muenchendigital.digiwf.email.integration.adapter.out.ProcessAdapter;
import io.muenchendigital.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import io.muenchendigital.digiwf.email.integration.model.FileAttachment;
import io.muenchendigital.digiwf.email.integration.model.Mail;
import io.muenchendigital.digiwf.email.integration.model.PresignedUrl;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.impl.ProcessApiImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SendMailUseCaseTest {

    private final MessageApi messageApi = Mockito.spy(Mockito.mock(MessageApi.class));

    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final LoadMailAttachmentPort loadMailAttachmentPort = mock(LoadMailAttachmentPort.class);

    private final ProcessApi processApi = new ProcessApiImpl(
            this.messageApi,
            "correlateMessageDestination",
            "startProcessDestination"
    );

    private final CorrelateMessagePort correlateMessagePort = new ProcessAdapter(processApi);
    private final String fromAddress = "digiwf@muenchen.de";

    private final Mail mail = new Mail(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "This is a test mail",
            "digiwf@muenchen.de",
            null
    );

    @BeforeEach
    void setUp() {
        String anhangInhalt = "Anhang Inhalt";
        byte[] anhangByt = anhangInhalt.getBytes();
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        when(this.loadMailAttachmentPort.loadAttachment(any())).thenReturn(new FileAttachment("Testanhang", new ByteArrayDataSource(anhangByt, "AttName")));
    }

    @Test
    void sendMail() throws MessagingException {

        final SendMailUseCase sendMailUseCase = new SendMailUseCase(this.javaMailSender, this.loadMailAttachmentPort, this.correlateMessagePort, fromAddress);

        sendMailUseCase.sendMail("processInstanceIde", "messageName", this.mail);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);

        verify(this.javaMailSender).send(messageArgumentCaptor.capture());
        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals("Test Mail", messageArgumentCaptor.getValue().getSubject());
    }

    @Test
    void sendMailWithAttachments() throws MessagingException {
        final Mail mailWithAttachments = this.mail;
        mailWithAttachments.setAttachments(List.of(new PresignedUrl("http://localhost:9000/some-url", "test.txt", "GET")));
        final SendMailUseCase sendMailUseCase = new SendMailUseCase(this.javaMailSender, this.loadMailAttachmentPort, this.correlateMessagePort, this.fromAddress);
        sendMailUseCase.sendMail("processInstanceIde", "messageName", mailWithAttachments);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());
        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals("Test Mail", messageArgumentCaptor.getValue().getSubject());
        // attachment
        verify(this.loadMailAttachmentPort, times(1)).loadAttachment(any());
        final FileAttachment result = this.loadMailAttachmentPort.loadAttachment(any());
        Assertions.assertEquals("Testanhang", result.getFileName());
    }

    @Test
    void testThatABpmnErrorIsThrowIfSendMailFailsWithAMailException() {
        doThrow(mock(MailException.class)).when(this.javaMailSender).send(any(MimeMessage.class));
        final SendMailUseCase sendMailUseCase = new SendMailUseCase(this.javaMailSender, this.loadMailAttachmentPort, this.correlateMessagePort, this.fromAddress);

        Assertions.assertThrows(BpmnError.class, () -> {
            sendMailUseCase.sendMail("processInstanceIde", "messageName", this.mail);
        });
    }


}
