package de.muenchen.oss.digiwf.cockpit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("digiwf.camunda-webapps")
@ConstructorBinding
@Data
public class CamundaWebappsProperties {
  /**
   * The role required to access camunda webapp.
   */
  private final String webAppRole;
}
