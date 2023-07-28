/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.schema.registry.internal.impl.repository;

import de.muenchen.oss.digiwf.schema.registry.internal.impl.model.JsonSchemaImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link JsonSchemaImpl}
 *
 * @author externer.dl.horn
 */
public interface JsonSchemaRepository extends JpaRepository<JsonSchemaImpl, String> {

    Optional<JsonSchemaImpl> findByKey(String key);
}
