/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.infrastructure.repository;

import io.muenchendigital.digiwf.service.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ServiceInstanceAuthorizationEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceAuthorizationRepository extends JpaRepository<ServiceInstanceAuthorizationEntity, String> {

    List<ServiceInstanceAuthorizationEntity> findAllByUserId(String userId);

    Optional<ServiceInstanceAuthorizationEntity> findByUserIdAndProcessInstanceId(String userId, String processInstanceId);
}
