/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ALW Personeninfo request data class.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AlwPersoneninfoRequest {

    /**
     * The AZR number.
     */
    private String azrNummer;

}