/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.adapter.out.alw;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Configuration for ALW-Integration REST client.
 */
@Data
@AllArgsConstructor
public class AlwResponsibilityRestConfig {

    private String baseurl;
    private String restEndpoint;
}
