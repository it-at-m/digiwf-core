package io.muenchendigital.digiwf.verification.integration.registration.api;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.service.IncidentService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import io.muenchendigital.digiwf.verification.integration.registration.domain.exception.RegistrationException;
import io.muenchendigital.digiwf.verification.integration.registration.domain.model.Registration;
import io.muenchendigital.digiwf.verification.integration.registration.domain.service.RegistrationService;
import io.muenchendigital.digiwf.verification.integration.shared.StreamingConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Processes the payload incoming from the event bus.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final RegistrationService registrationService;
    private final CorrelateMessageService correlateMessageService;
    private final IncidentService incidentService;
    private static final String VERIFICATION_LINK = "verificationLink";

    /**
     * All messages from the route "register" go here.
     *
     * @return  the consumer
     */
    @Bean
    public Consumer<Message<Registration>> getVerificationLink() {
        return message -> {
            log.info("Processing new registration from eventbus");
            final Registration registration = message.getPayload();
            log.debug("Registration: {}", registration);
            try {
                final String link = registrationService.getVerificationLink(registration);
                emitResponse(message.getHeaders(), link);
            } catch (final Exception e) {
                log.error("Registration failed: {}", e.getMessage());
                if (!emitError(message.getHeaders(), "Registration failed" )){
                    log.error("Emitting registration error failed");
                }
            }
        };
    }

    /**
     * Function to emit a reponse using the correlateMessageService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders    The MessageHeaders of the incoming message you want to correlate your answer to
     * @param verificationLink  the link to verify one's declarations
     */
    public void emitResponse(final MessageHeaders messageHeaders, final String verificationLink) {
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put(VERIFICATION_LINK, verificationLink);
        if (!correlateMessageService.sendCorrelateMessage(messageHeaders, correlatePayload)){
            throw new RuntimeException("Emitting response failed");
        }
    }

    /**
     * Function to emit an incident using the incidentService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     * @param message        The error message
     */
    public boolean emitError(final MessageHeaders messageHeaders, final String message) {
        return incidentService.sendIncident(messageHeaders, message);
    }
}
