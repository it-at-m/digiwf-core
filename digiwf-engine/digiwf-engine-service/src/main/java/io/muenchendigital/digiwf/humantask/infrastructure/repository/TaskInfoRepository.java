/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to perform db operation on a {@link TaskInfoEntity}
 *
 * @author externer.dl.horn
 */
public interface TaskInfoRepository extends JpaRepository<TaskInfoEntity, String> {

}
