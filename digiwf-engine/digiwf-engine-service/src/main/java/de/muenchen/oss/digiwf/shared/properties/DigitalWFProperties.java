/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.shared.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * DigitalWF properties.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "digiwf.base")
public class DigitalWFProperties {

    /**
     * Url of the frontend.
     */
    @NotBlank
    private String frontendUrl;

}
