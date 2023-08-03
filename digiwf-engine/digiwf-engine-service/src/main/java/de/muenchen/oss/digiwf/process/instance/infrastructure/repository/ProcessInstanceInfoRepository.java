/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.infrastructure.repository;

import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ServiceInstanceEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceInfoRepository extends JpaRepository<ServiceInstanceEntity, String> {

    Optional<ServiceInstanceEntity> findByInstanceId(String processInstanceId);

    List<ServiceInstanceEntity> findAllByInstanceIdIn(List<String> instanceIds);

    List<ServiceInstanceEntity> findByRemovalTimeBefore(Date referenceDate);
}
