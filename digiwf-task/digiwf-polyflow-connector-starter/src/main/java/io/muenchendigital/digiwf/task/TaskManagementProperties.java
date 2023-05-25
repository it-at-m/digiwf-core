package io.muenchendigital.digiwf.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "digiwf.task-management")
@Data
public class TaskManagementProperties {
  @NestedConfigurationProperty
  private AssignmentProperties assignment = new AssignmentProperties();

  @Data
  public static class AssignmentProperties {
    private boolean shadow;
    private boolean local;
    private boolean delete;
  }
}
