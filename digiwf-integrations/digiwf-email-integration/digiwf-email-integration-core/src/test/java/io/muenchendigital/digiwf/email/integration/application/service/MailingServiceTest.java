package io.muenchendigital.digiwf.email.integration.application.service;

import io.muenchendigital.digiwf.email.integration.application.dto.AttachmentDto;
import io.muenchendigital.digiwf.email.integration.application.dto.MailDto;
import io.muenchendigital.digiwf.email.integration.application.model.Attachment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailingServiceTest {

    private final DummyMailAttachmentPort dummyMailAttachmentPort = spy(new DummyMailAttachmentPort("test.txt"));
    @Mock
    private JavaMailSender javaMailSender;
    private final String fromAddress = "digiwf@muenchen.de";

    @BeforeEach
    void setUp() {
        when(this.javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
    }

    private final MailDto mail = new MailDto(
            "mailReceiver1@muenchen.de,mailReceiver2@muenchen.de",
            "receiverCC@muenchen.de",
            "receiverBCC@muenchen.de",
            "Test Mail",
            "This is a test mail",
            "digiwf@muenchen.de",
            null
    );

    @Test
    void sendMail() throws MessagingException {
        final MailingService mailingService = new MailingService(this.javaMailSender, this.dummyMailAttachmentPort, this.fromAddress);
        mailingService.sendMail(this.mail);
        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);

        verify(this.javaMailSender).send(messageArgumentCaptor.capture());
        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals("Test Mail", messageArgumentCaptor.getValue().getSubject());
    }

    @Test
    void sendMailWithAttachments() throws MessagingException {
        final MailDto mailWithAttachments = this.mail;
        mailWithAttachments.setAttachments(List.of(new AttachmentDto("http://localhost:9000/some-url", "test.txt", "GET")));
        final MailingService mailingService = new MailingService(this.javaMailSender, this.dummyMailAttachmentPort, this.fromAddress);
        mailingService.sendMail(mailWithAttachments);

        final ArgumentCaptor<MimeMessage> messageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(this.javaMailSender).send(messageArgumentCaptor.capture());
        Assertions.assertEquals(4, messageArgumentCaptor.getValue().getAllRecipients().length);
        Assertions.assertEquals(1, messageArgumentCaptor.getValue().getReplyTo().length);
        Assertions.assertEquals("Test Mail", messageArgumentCaptor.getValue().getSubject());
        // attachment
        verify(this.dummyMailAttachmentPort, times(1)).loadAttachement(any());
        final Attachment result = this.dummyMailAttachmentPort.loadAttachement(any());
        Assertions.assertEquals("test.txt", result.getFileName());
    }

}
