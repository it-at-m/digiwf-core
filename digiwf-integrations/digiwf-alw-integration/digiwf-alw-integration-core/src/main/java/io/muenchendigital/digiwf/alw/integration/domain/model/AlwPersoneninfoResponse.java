/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ALW Personeninfo response data class.
 */
@Data
@AllArgsConstructor
public class AlwPersoneninfoResponse {

    /**
     * Responsible organisational unit.
     */
    private final String zustaendigeGruppe;

}