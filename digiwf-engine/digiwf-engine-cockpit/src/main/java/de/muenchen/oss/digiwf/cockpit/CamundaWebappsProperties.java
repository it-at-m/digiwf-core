package de.muenchen.oss.digiwf.cockpit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("digiwf.camunda-webapps")
@Data
public class CamundaWebappsProperties {
  /**
   * The role required to access camunda webapp.
   */
  private final String webAppRole;
}
