package de.muenchen.oss.digiwf.shared.configuration.camunda;

import org.camunda.bpm.engine.rest.exception.RestExceptionHandler;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
public class CamundaRestExceptionHandlerConfiguration {
  // Quite a dirty hack to replace camunda's RestExceptionHandler with our own that logs exceptions more selectively.
  // Workaround for https://app.camunda.com/jira/browse/CAM-10799.
  @PostConstruct
  public void replaceRestExceptionHandler() {
    Set<Class<?>> configurationClasses = CamundaRestResources.getConfigurationClasses();
    configurationClasses.remove(org.camunda.bpm.engine.rest.exception.RestExceptionHandler.class);
    configurationClasses.add(RestExceptionHandler.class);
  }
}
