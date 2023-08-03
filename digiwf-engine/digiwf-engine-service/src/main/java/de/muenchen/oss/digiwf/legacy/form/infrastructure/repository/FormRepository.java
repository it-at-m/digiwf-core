/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.infrastructure.repository;

import de.muenchen.oss.digiwf.legacy.form.infrastructure.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link FormEntity}
 *
 * @author externer.dl.horn
 */
public interface FormRepository extends JpaRepository<FormEntity, String> {

    Optional<FormEntity> findByKey(String key);
}
