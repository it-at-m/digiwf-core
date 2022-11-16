package io.muenchendigital.digiwf.verification.integration.registration.domain.service;

import io.muenchendigital.digiwf.verification.integration.registration.domain.exception.RegistrationException;
import io.muenchendigital.digiwf.verification.integration.registration.domain.model.Registration;
import io.muenchendigital.digiwf.verification.integration.shared.domain.entity.VerificationEntity;
import io.muenchendigital.digiwf.verification.integration.shared.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Service to register a verification.
 */
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    final VerificationRepository verificationRepository;
    final LinkService linkService;

    /**
     * Validates and persists a registration returning a unique link for verification.
     *
     * @param registration  all informations for the expected verification
     * @return  the verification link to the service
     */
    public String getVerificationLink(final Registration registration) throws RegistrationException {
        log.debug("Get verification link for: {} ({})", registration.getProcessInstanceId(), registration.getMessageName());

        if (StringUtils.isEmpty(registration.getMessageName())) {
            throw new RegistrationException("No correlation key provided");
        }
        if (verificationRepository.findByProcessInstanceIdAndMessageName(registration.getProcessInstanceId(), registration.getMessageName()).isPresent()){
            throw new RegistrationException("Correlation key already exists");
        }
        final UUID token = generateToken();
        persistVerification(registration, token);

        final String link = linkService.generateLink(token);
        log.info("Generated link: {}", link);
        return link;
    }

    private void persistVerification(final Registration registration, final UUID token) {
        final VerificationEntity verificationEntity = VerificationEntity.builder()
                .messageName(registration.getMessageName())
                .processInstanceId(registration.getProcessInstanceId())
                .expiryTime(registration.getExpiryTime())
                .token(token.toString())
                .build();

        verificationRepository.save(verificationEntity);
    }

    private UUID generateToken() {
        return UUID.randomUUID();
    }

}
