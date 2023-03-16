package io.muenchendigital.digiwf.email.integration.application;

import io.muenchendigital.digiwf.email.integration.application.dto.MailDto;
import io.muenchendigital.digiwf.email.integration.application.service.MailingService;
import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailIntegration {

    private final MailingService mailingService;

    @DigiwfIntegration(type = "sendMailFromEventBus")
    public Map<String, Object> sendMail(final MailDto mail) throws MessagingException {
        log.info("Processing new mail from eventbus");
        log.debug("Mail: {}", mail);

        this.mailingService.sendMail(mail);

        return Map.of("mailSentStatus", true);
    }

}
