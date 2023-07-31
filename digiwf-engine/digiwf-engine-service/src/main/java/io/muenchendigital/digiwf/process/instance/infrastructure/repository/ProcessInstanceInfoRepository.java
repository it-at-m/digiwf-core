/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.instance.infrastructure.repository;

import io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    @Query(
            value = "SELECT si FROM io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity si LEFT JOIN io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity sia ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user",
            countQuery = "SELECT count(si) FROM io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity si LEFT JOIN io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity sia ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user"
    )
    Page<ServiceInstanceEntity> findAllByUserId(@Param("user") String userId, Pageable pageable);

    @Query(
            value = "SELECT si FROM io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity si LEFT JOIN io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity sia ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user AND (" +
                    "lower(si.id) LIKE concat('%', :search,'%')" +
                    "OR lower(si.instanceId) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionName) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionKey) LIKE concat('%', :search,'%')" +
                    "OR lower(si.description) LIKE concat('%', :search,'%')" +
                    "OR lower(si.status) LIKE concat('%', :search,'%')" +
                    "OR lower(si.statusKey) LIKE concat('%', :search,'%')" +
                    ")",
            countQuery = "SELECT count(si) FROM io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity si LEFT JOIN io.muenchendigital.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity sia ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user AND (" +
                    "lower(si.id) LIKE concat('%', :search,'%')" +
                    "OR lower(si.instanceId) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionName) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionKey) LIKE concat('%', :search,'%')" +
                    "OR lower(si.description) LIKE concat('%', :search,'%')" +
                    "OR lower(si.status) LIKE concat('%', :search,'%')" +
                    "OR lower(si.statusKey) LIKE concat('%', :search,'%')" +
                    ")"
    )
    Page<ServiceInstanceEntity> searchAllByUserId(
            @Param("search") String lowerQuery,
            @Param("user") String userId,
            Pageable pageable
    );
}
