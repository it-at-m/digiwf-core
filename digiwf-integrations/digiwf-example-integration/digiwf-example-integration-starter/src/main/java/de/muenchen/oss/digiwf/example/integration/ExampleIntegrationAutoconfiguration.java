package de.muenchen.oss.digiwf.example.integration;

import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ExampleOutPort;
import de.muenchen.oss.digiwf.example.integration.core.application.usecase.ExampleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"de.muenchen.oss.digiwf.example.integration.core"})
public class ExampleIntegrationAutoconfiguration {

    @ConditionalOnMissingBean
    @Bean
    public ExampleInPort exampleInPort(final ExampleOutPort exampleOutPort) {
        return new ExampleUseCase(exampleOutPort);
    }
}
