/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.alw.personeninfo")
@Data
public class AlwPersoneninfoProperties {

  private String baseurl;
  private Integer port;
  private String restEndpoint;
  private Integer timeout;
  private String username;
  private String password;
  @NestedConfigurationProperty
  private FunctionalPingConfig functionalPing = new FunctionalPingConfig();

  @Data
  public static class FunctionalPingConfig {
    private boolean enabled = false;
    private String azrNumber = null;
  }
}
