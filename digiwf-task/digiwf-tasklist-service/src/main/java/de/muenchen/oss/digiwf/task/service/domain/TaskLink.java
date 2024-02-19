package de.muenchen.oss.digiwf.task.service.domain;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Map;

@Data
public class TaskLink {
  @NonNull
  private final String type;
  private final String url;
  private final String label;
  private final String htmlContent;
  private final Map<String, String> additionalParameters;
}
