/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.infrastructure.repository;

import de.muenchen.oss.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to perform db operation on a {@link TaskInfoEntity}
 *
 * @author externer.dl.horn
 */
public interface TaskInfoRepository extends JpaRepository<TaskInfoEntity, String> {

}
