/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.alwdms.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties for the alw dms.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "digiwf.alw.dms")
public class AlwDmsProperties {

    /**
     * Url of the eai.
     */
    @NotBlank
    private String eaiUrl;

    /**
     * Url of the dms application (ui).
     */
    @NotBlank
    private String uiurl;

    /**
     * Default user for
     */
    private String defaultUser;

}
