/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.infrastructure.repository;

import de.muenchen.oss.digiwf.filters.infrastructure.entity.FilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository to perform db operation on a {@link FilterEntity}
 */
public interface FilterRepository extends JpaRepository<FilterEntity, String> {

    List<FilterEntity> findByUserId(String userId);
}
