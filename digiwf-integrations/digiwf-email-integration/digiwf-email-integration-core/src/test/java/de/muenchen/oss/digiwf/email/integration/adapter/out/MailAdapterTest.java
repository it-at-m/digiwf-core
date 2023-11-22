package de.muenchen.oss.digiwf.email.integration.adapter.out;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MailAdapterTest {

    private final DigiwfEmailApi digiwfEmailApi = mock(DigiwfEmailApi.class);

    @Test
    void sendMail() throws MessagingException {
        final MailAdapter mailAdapter = new MailAdapter(digiwfEmailApi);
        mailAdapter.sendMail("receivers", "subject", "body", "replyTo", "receiversCc", "receiversBcc", null);
        verify(digiwfEmailApi).sendMailWithAttachments("receivers", "subject", "body", "replyTo", "receiversCc", "receiversBcc", null);
    }

}
