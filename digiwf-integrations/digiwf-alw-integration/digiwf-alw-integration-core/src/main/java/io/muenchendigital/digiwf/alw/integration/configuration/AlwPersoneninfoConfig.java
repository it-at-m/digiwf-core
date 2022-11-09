/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Config beans for ALW-Integration.
 */
@Data
@AllArgsConstructor
public class AlwPersoneninfoConfig {

    private String baseurl;
    private String restEndpoint;
}
