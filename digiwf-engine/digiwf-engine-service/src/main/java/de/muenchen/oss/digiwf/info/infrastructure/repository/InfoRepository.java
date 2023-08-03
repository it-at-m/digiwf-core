/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.info.infrastructure.repository;

import de.muenchen.oss.digiwf.info.infrastructure.entity.InfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to perform db operation on a {@link InfoEntity}
 *
 * @author martinInfoService.dietrich
 */
public interface InfoRepository extends JpaRepository<InfoEntity, String> {

    InfoEntity findFirstBy();
}
