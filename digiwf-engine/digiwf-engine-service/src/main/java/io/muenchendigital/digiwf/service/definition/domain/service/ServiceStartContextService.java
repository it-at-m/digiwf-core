/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.definition.domain.service;

import io.muenchendigital.digiwf.service.definition.domain.model.StartContext;
import io.muenchendigital.digiwf.service.definition.infrastructure.repository.StartContextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to handle files in service starts in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceStartContextService {

    private final StartContextRepository startContextRepository;

    public Optional<StartContext> getStartContext(final String userId, final String definitionKey) {
        return this.startContextRepository.findByUserIdAndDefinitionKey(userId, definitionKey);
    }

    public StartContext createStartContext(final String userId, final String definitionKey) {
        final StartContext newContext = new StartContext(userId, definitionKey);
        return this.startContextRepository.save(newContext);
    }

    public void deleteStartContext(final String userId, final String definitionKey) {
        this.getStartContext(userId, definitionKey).ifPresent(this.startContextRepository::delete);
    }

}
