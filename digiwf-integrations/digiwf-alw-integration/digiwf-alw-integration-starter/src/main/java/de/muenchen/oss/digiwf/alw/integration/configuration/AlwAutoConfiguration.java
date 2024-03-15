/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import de.muenchen.oss.digiwf.alw.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityEmulationAdapter;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityRestAdapter;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.alw.AlwResponsibilityRestConfig;
import de.muenchen.oss.digiwf.alw.integration.adapter.out.integration.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.alw.integration.application.port.in.GetResponsibilityInPort;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.model.AlwPingConfig;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;


@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"de.muenchen.oss.digiwf.alw.integration"})
@EnableConfigurationProperties({
        AlwPersoneninfoProperties.class,
})
public class AlwAutoConfiguration {

    private final AlwPersoneninfoProperties alwPersoneninfoProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .basicAuthentication(alwPersoneninfoProperties.getUsername(), alwPersoneninfoProperties.getPassword(), Charset.defaultCharset())
                .setConnectTimeout(Duration.of(alwPersoneninfoProperties.getTimeout(), ChronoUnit.MILLIS))
                .build();
    }

    @Bean
    public AlwResponsibilityRestConfig alwConfig() {
        return new AlwResponsibilityRestConfig(
                alwPersoneninfoProperties.getBaseurl(),
                alwPersoneninfoProperties.getRestEndpoint()
        );
    }

    @Bean
    public AlwPingConfig alwPingConfig() {
        return new AlwPingConfig(
                alwPersoneninfoProperties.getFunctionalPing().isEnabled(),
                alwPersoneninfoProperties.getFunctionalPing().getAzrNumber()
        );
    }

    @Bean
    public IntegrationOutPort integration(ProcessApi processApi, ErrorApi errorApi) {
        return new IntegrationOutAdapter(processApi, errorApi);
    }

    @ConditionalOnMissingBean
    @Bean
    public MessageProcessor messageProcessor(final IntegrationOutPort integration,
                                             final GetResponsibilityInPort getResponsibilityInPort) {
        return new MessageProcessor(integration, getResponsibilityInPort);
    }

    @Bean
    public Consumer<Message<ResponsibilityRequest>> getAlwResponsibility(final MessageProcessor messageProcessor) {
        return messageProcessor.getAlwResponsibility();
    }

    @ConditionalOnMissingBean
    @Profile("!alw-emulation")
    @Bean
    public AlwResponsibilityRestAdapter alwResponsibilityRestAdapter(final RestTemplate restTemplate,
                                                                     final AlwResponsibilityRestConfig alwResponsibilityRestConfig) {
        return new AlwResponsibilityRestAdapter(restTemplate, alwResponsibilityRestConfig);
    }

    @ConditionalOnMissingBean
    @Profile("alw-emulation")
    @Bean
    public AlwResponsibilityEmulationAdapter alwResponsibilityEmulationAdapter(final AlwResponsibilityRestConfig alwResponsibilityRestConfig) {
        return new AlwResponsibilityEmulationAdapter(alwResponsibilityRestConfig);
    }
}
