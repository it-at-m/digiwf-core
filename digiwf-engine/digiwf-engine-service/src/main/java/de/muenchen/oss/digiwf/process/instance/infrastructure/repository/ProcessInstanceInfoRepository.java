/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.infrastructure.repository;

import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ServiceInstanceEntity}
 *
 * @author externer.dl.horn
 */
@Repository
public interface ProcessInstanceInfoRepository extends JpaRepository<ServiceInstanceEntity, String> {

    Optional<ServiceInstanceEntity> findByInstanceId(String processInstanceId);

    List<ServiceInstanceEntity> findByRemovalTimeBefore(Date referenceDate);

    /**
     *
     * @param userId id of the user
     * @param pageable page request
     * @return all process information which th user with the given id is allowed to see
     */
    @Query(
            value = "SELECT si FROM ServiceInstance si " +
                    "LEFT JOIN ProcessInstanceAuth sia ON sia.processInstanceId = si.instanceId " +
                    "WHERE sia.userId = :user",
            countQuery = "SELECT count(si) FROM ServiceInstance si " +
                    "LEFT JOIN ProcessInstanceAuth sia ON sia.processInstanceId = si.instanceId " +
                    "WHERE sia.userId = :user"
    )
    Page<ServiceInstanceEntity> findAllByUserId(@Param("user") String userId, Pageable pageable);

    /**
     *
     * @param lowerQuery search string in lower case
     * @param userId id of the user
     * @param pageable page request
     * @return all process information which th user with the given id is allowed to see and which contains the search query in the following columns
     * - id
     * - instanceId
     * - definitionName
     * - definitionKey
     * - description
     * - status
     * - statusKey
     */
    @Query(
            value = "SELECT si FROM ServiceInstance si " +
                    "LEFT JOIN ProcessInstanceAuth sia " +
                    "ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user AND (" +
                    "lower(si.id) LIKE concat('%', :search,'%')" +
                    "OR lower(si.instanceId) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionName) LIKE concat('%', :search,'%')" +
                    "OR lower(si.definitionKey) LIKE concat('%', :search,'%')" +
                    "OR lower(si.description) LIKE concat('%', :search,'%')" +
                    "OR lower(si.status) LIKE concat('%', :search,'%')" +
                    "OR lower(si.statusKey) LIKE concat('%', :search,'%')" +
                    ")",
            countQuery = "SELECT count(si) FROM ServiceInstance si " +
                    "LEFT JOIN ProcessInstanceAuth sia ON sia.processInstanceId = si.instanceId WHERE sia.userId = :user AND (" +
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
