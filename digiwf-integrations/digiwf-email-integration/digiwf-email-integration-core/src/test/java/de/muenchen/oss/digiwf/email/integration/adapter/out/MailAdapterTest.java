package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MailAdapterTest {

    private final DigiwfEmailApi digiwfEmailApi = mock(DigiwfEmailApi.class);

    @Test
    void sendMail() throws MessagingException {
        final MailAdapter mailAdapter = new MailAdapter(digiwfEmailApi);
        final Mail mail = Mail.builder()
                .receivers("receivers")
                .subject("subject")
                .body("body")
                .replyTo("replyTo")
                .receiversCc("receiversCc")
                .receiversBcc("receiversBcc")
                .build();
        mailAdapter.sendMail(mail);
        verify(digiwfEmailApi).sendMail(mail);
    }

}
