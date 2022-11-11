package io.muenchendigital.digiwf.verification.integration.verification.domain.service;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import io.muenchendigital.digiwf.verification.integration.shared.StreamingConstants;
import io.muenchendigital.digiwf.verification.integration.shared.domain.entity.VerificationEntity;
import io.muenchendigital.digiwf.verification.integration.shared.repository.VerificationRepository;
import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.CorrelationException;
import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.VerificationExpiredException;
import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.VerificationTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Validates token and sends feedback to event bus.
 */
@RequiredArgsConstructor
@Slf4j
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final CorrelateMessageService correlateMessageService;

    /**
     * Verifies an incoming token and sends the deposited data to the event bus.
     *
     * @param token     the unique token to validate
     * @throws VerificationTokenNotFoundException   when token is not found in storage
     * @throws VerificationExpiredException     when the expiryTime of the verification is reached
     * @throws CorrelationException     when the delivery to the event bus failed
     */
    public void verify(final String token) throws VerificationTokenNotFoundException, VerificationExpiredException, CorrelationException {
        log.debug("Verifying token: {}", token);
        final Optional<VerificationEntity> verificationEntity = verificationRepository.findByToken(token);

        if (verificationEntity.isEmpty()){
            throw new VerificationTokenNotFoundException();
        }

        if (verificationEntity.get().getExpiryTime() != null && LocalDateTime.now().isAfter(verificationEntity.get().getExpiryTime())){
            throw new VerificationExpiredException();
        }

        sendCorrelateMessage(verificationEntity.get());

        deleteVerification(verificationEntity.get());
        log.debug("Verification finished");
    }

    private void deleteVerification(final VerificationEntity verificationEntity) {
        verificationRepository.delete(verificationEntity);
    }

    private void sendCorrelateMessage(final VerificationEntity verificationEntity) throws CorrelationException {
        log.info("Correlate verification to process instance: {} ({})", verificationEntity.getProcessInstanceId(), verificationEntity.getMessageName());
        final Map<String,Object> headers = new HashMap<>();
        headers.put(StreamingConstants.HEADER_PROCESS_INSTANCE_ID, verificationEntity.getProcessInstanceId());
        headers.put(StreamingConstants.HEADER_MESSAGE_NAME, verificationEntity.getMessageName());
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put(StreamingConstants.PROPERTY_SUBJECT, verificationEntity.getSubject());
        final boolean result = correlateMessageService.sendCorrelateMessage(new MessageHeaders(headers), correlatePayload);
        if (!result) {
            throw new CorrelationException();
        }
        log.debug("Correlation successful");
    }

}
