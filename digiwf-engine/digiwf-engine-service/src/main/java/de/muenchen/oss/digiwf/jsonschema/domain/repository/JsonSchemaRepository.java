/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.jsonschema.domain.repository;

import de.muenchen.oss.digiwf.jsonschema.domain.model.JsonSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link JsonSchema}
 *
 * @author externer.dl.horn
 */
public interface JsonSchemaRepository extends JpaRepository<JsonSchema, String> {

    Optional<JsonSchema> findByKey(String key);
}
