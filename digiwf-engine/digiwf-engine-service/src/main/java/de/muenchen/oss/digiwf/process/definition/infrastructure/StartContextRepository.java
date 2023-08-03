/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.definition.infrastructure;

import de.muenchen.oss.digiwf.process.definition.domain.model.StartContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operations on a {@link StartContext}
 *
 * @author externer.dl.horn
 */
public interface StartContextRepository extends JpaRepository<StartContext, String> {

    Optional<StartContext> findByUserIdAndDefinitionKey(String userId, String definitionKey);
}
