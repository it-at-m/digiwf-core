/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.domain.mapper;

import de.muenchen.oss.digiwf.alw.integration.domain.model.AlwPersoneninfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapping ALW system response to a structure with a valid directory-ou.
 */
@Component
@RequiredArgsConstructor
public class AlwPersoneninfoResponseMapper {

    private final SachbearbeitungMapper mapper;

    public AlwPersoneninfoResponse map(final String sachbearbeitung) {
        final String responsibleGroup = mapper.map(sachbearbeitung);
        return new AlwPersoneninfoResponse(responsibleGroup);
    }
}
