/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.domain.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Mapping of sachbearbeitung string to directory-ou.
 */
@Slf4j
@RequiredArgsConstructor
public class SachbearbeitungMapper {

    private final Map<String, String> sachbearbeitungMap;

    public boolean isInitialized() {
        return !sachbearbeitungMap.isEmpty();
    }

    public String map(final String sachbearbeitung) {
        String mappingResult = null;
        if (sachbearbeitung != null) {
            if (!sachbearbeitungMap.containsKey(sachbearbeitung)) {
                log.warn("No mapping entry for sachbearbeitung: {}", sachbearbeitung);
            }
            mappingResult = sachbearbeitungMap.get(sachbearbeitung);
        } else {
            log.info("Parameter sachbearbeitung is empty");
        }
        return mappingResult;
    }

}
