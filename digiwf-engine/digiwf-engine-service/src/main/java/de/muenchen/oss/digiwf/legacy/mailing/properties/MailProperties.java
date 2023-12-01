/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.mailing.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Mail properties to set environment configurations.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "digiwf.mail")
public class MailProperties {

    /**
     * Default sender address.
     */
    @NotBlank
    private String defaultFromAddress;

    /**
     * Indicates if receivers should be overwritten.
     * Used e.g. in test environments
     */
    private boolean overrideReceivers;

    /**
     * Default receivers.
     * Are set when overrideReceivers is active
     */
    private String defaultReceiverAddress;

}
