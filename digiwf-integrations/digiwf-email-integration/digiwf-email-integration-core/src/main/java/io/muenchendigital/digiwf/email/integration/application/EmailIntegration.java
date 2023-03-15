package io.muenchendigital.digiwf.email.integration.application;

import io.muenchendigital.digiwf.email.integration.application.service.MailingService;
import io.muenchendigital.digiwf.email.integration.domain.Mail;
import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailIntegration {

    private final MailingService mailingService;

    @DigiwfIntegration(type = "sendMailFromEventBus")
    public Map<String, Object> sendMail(final Mail mail) {
        log.info("Processing new mail from eventbus");
        log.debug("Mail: {}", mail);

        this.mailingService.sendMail(mail);

        return Map.of("mailSentStatus", true);
    }

}
