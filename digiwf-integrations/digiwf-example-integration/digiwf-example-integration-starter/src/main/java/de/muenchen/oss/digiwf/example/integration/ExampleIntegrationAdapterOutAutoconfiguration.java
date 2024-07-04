package de.muenchen.oss.digiwf.example.integration;

import de.muenchen.oss.digiwf.example.integration.core.adapter.out.example.ExampleAdapter;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ExampleOutPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleIntegrationAdapterOutAutoconfiguration {

    @ConditionalOnMissingBean
    @Bean
    public ExampleOutPort exampleOutPort() {
        return new ExampleAdapter();
    }
}
