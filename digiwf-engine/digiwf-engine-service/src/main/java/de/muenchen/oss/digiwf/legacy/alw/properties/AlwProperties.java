/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.alw.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Properties to configure the alw domain.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "digiwf.alw.personeninfo")
public class AlwProperties {

    /**
     * Url of the external eai.
     */
    @NotBlank
    private String eaiUrl;

}
