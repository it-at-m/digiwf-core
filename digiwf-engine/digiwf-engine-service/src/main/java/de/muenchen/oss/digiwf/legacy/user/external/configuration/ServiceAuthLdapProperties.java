/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.user.external.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;

/**
 * This class provides the ldap connection properties given in the application.yml.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "directory.ldap")
public class ServiceAuthLdapProperties {

    @NotBlank
    private String contextSource;

    @NotBlank
    private String personSearchBase;

    @NotBlank
    private String personObjectClasses;

    @NotBlank
    private String ouSearchBase;

    @NotBlank
    private String ouObjectClasses;

}
