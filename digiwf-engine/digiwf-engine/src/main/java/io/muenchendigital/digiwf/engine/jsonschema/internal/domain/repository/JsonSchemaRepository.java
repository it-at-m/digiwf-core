/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.engine.jsonschema.internal.domain.repository;

import io.muenchendigital.digiwf.engine.jsonschema.internal.domain.model.JsonSchema;
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
