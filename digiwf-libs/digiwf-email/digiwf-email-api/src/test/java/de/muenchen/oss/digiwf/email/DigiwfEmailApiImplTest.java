package de.muenchen.oss.digiwf.email;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.impl.DigiwfEmailApiImpl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.Mockito.*;

class DigiwfEmailApiImplTest {


    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private DigiwfEmailApi digiwfEmailApi;

    // test data
    private final String receiver = "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de";
    private final String receiverCC = "receiverCC@muenchen.de";
    private final String receiverBCC = "receiverBCC@muenchen.de";
    private final String subject = "Test Mail";
    private final String body = "This is a test mail";
    private final String replyTo = "digiwf@muenchen.de";


    @BeforeEach
    void setUp() {
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        this.digiwfEmailApi = new DigiwfEmailApiImpl(this.javaMailSender, "digiwf@muenchen.de");
    }

    @Test
    void sendSimpleMail() throws MessagingException {
        this.digiwfEmailApi.sendMail(this.receiver, this.subject, this.body, this.replyTo);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());

        Assertions.assertEquals(2, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals(this.subject, messageArgumentCaptor.getValue().getSubject());
    }

    @Test
    void sendMailWithCCAndBCC() throws MessagingException {
        this.digiwfEmailApi.sendMail(this.receiver, this.subject, this.body, this.replyTo, this.receiverCC, this.receiverBCC);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());

        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals(this.subject, messageArgumentCaptor.getValue().getSubject());
    }

    @Test
    void sendMailWithAttachments() throws MessagingException {
        final List<FileAttachment> fileAttachments = List.of(
                new FileAttachment("Testanhang", new ByteArrayDataSource("FooBar".getBytes(), "text/plain"))
        );
        this.digiwfEmailApi.sendMailWithAttachments(this.receiver, this.subject, this.body, this.replyTo, fileAttachments);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());

        Assertions.assertEquals(2, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals(this.subject, messageArgumentCaptor.getValue().getSubject());
    }

    @Test
    void sendMailWithReceiversCCAndBCCAndAttachments() throws MessagingException {
        final List<FileAttachment> fileAttachments = List.of(
                new FileAttachment("Testanhang", new ByteArrayDataSource("FooBar".getBytes(), "text/plain"))
        );
        this.digiwfEmailApi.sendMailWithAttachments(this.receiver, this.subject, this.body, this.replyTo, this.receiverCC, this.receiverBCC, fileAttachments);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());

        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals(this.subject, messageArgumentCaptor.getValue().getSubject());
    }

}
