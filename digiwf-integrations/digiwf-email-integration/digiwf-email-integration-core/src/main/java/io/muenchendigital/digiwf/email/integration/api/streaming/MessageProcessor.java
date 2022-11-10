package io.muenchendigital.digiwf.email.integration.api.streaming;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {


    private final MailingService mailingService;
    private final CorrelateMessageService correlateMessageService;
    private static final String MAIL_SENT_STATUS = "mailSentStatus";

    /**
     * All messages from the route "sendMailFromEventBus" go here.
     *
     * @return the consumer
     */
    @Bean
    public Consumer<Message<Mail>> sendMailFromEventBus() {
        return message -> {
            log.info("Processing new mail from eventbus");
            final Mail mail = message.getPayload();
            log.debug("Mail: {}", mail);
            try {
                this.mailingService.sendMail(mail);
                this.emitResponse(message.getHeaders(), true);
            } catch (final RuntimeException e) {
                log.error("Mail could not be sent: {}", e.getMessage());
                this.emitResponse(message.getHeaders(), false);
            }
        };
    }

    /**
     * Function to emit a reponse using the correlateMessageService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     * @param status         true when the e-mail has been sent, false when an error occured
     */
    public void emitResponse(final MessageHeaders messageHeaders, final boolean status) {
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put(MAIL_SENT_STATUS, status);
        this.correlateMessageService.sendCorrelateMessage(messageHeaders, correlatePayload);
    }
}
