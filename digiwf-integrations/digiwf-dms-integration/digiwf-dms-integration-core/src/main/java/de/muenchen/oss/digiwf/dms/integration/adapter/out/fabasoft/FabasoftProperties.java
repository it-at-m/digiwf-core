/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "digiwf.integration.dms.fabasoft")
public class FabasoftProperties {

    @NotBlank
    private String address;

    @NotBlank
    private String username;

    private String password;

    private String businessapp;

    private Boolean enableMTOM;
}
