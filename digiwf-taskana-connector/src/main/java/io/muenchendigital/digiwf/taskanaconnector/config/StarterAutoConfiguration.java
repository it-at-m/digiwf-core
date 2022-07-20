package io.muenchendigital.digiwf.taskanaconnector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.muenchendigital.digiwf.taskanaconnector.parselistener.TaskanaParseListenerProcessEnginePlugin;

@EnableConfigurationProperties(StarterPluginProperties.class)
@Configuration
public class StarterAutoConfiguration {
  
    @Bean
    @ConditionalOnMissingBean
    public TaskanaParseListenerProcessEnginePlugin taskanaParseListenerProcessEnginePlugin(StarterPluginProperties pluginProperties) {
      if (pluginProperties.getOutgoingTopic() == null) {
        throw new IllegalArgumentException("The outgoingTopic property must be set");
      }

      return new TaskanaParseListenerProcessEnginePlugin(pluginProperties);
    }

}
