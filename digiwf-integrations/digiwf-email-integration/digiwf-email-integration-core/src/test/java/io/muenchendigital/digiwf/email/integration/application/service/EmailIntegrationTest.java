package io.muenchendigital.digiwf.email.integration.application.service;

import io.muenchendigital.digiwf.email.integration.application.EmailIntegration;
import io.muenchendigital.digiwf.email.integration.application.dto.MailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailIntegrationTest {

    private final MailingService mailingService = mock(MailingService.class);
    private EmailIntegration emailIntegration;

    private final MailDto mail = new MailDto(
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
        this.emailIntegration = new EmailIntegration(this.mailingService);
    }

    @Test
    void sendMail() throws MessagingException {
        this.emailIntegration.sendMail(this.mail);
        verify(this.mailingService, times(1)).sendMail(eq(this.mail));
    }


}
