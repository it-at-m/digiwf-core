package io.muenchendigital.digiwf.verification.integration.shared.repository;

import io.muenchendigital.digiwf.verification.integration.shared.domain.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to persist the verifications.
 */
public interface VerificationRepository extends JpaRepository<VerificationEntity, String> {

    Optional<VerificationEntity> findByProcessInstanceIdAndMessageName(String processInstanceId, String correlationKey);

    Optional<VerificationEntity> findByToken(String token);
}
