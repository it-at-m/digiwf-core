/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "digiwf.alw.personeninfo")
public class AlwPersoneninfoProperties {

    private String baseurl;
    private Integer port;
    private String restEndpoint;
    private Integer timeout;
    private String username;
    private String password;
    @NestedConfigurationProperty
    private FunctionalPingConfig functionalPing;

    @Data
    public static class FunctionalPingConfig {
        private boolean enabled;
        private String azrNumber;
    }
}
