package io.muenchendigital.digiwf.camunda.connector.configuration;

import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.camunda.connector.output.CamundaOutputClient;
import io.muenchendigital.digiwf.camunda.connector.output.CamundaOutputConfiguration;
import io.muenchendigital.digiwf.connector.api.output.OutputService;
import io.muenchendigital.digiwf.connector.output.internal.OutputServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.camunda.connector")
@EnableConfigurationProperties(DigiWFCamundaConnectorProperties.class)
public class DigiWFCamundaConnectorAutoConfiguration {


    @Value("#{'${io.muenchendigital.digiwf.camunda.connector.filtervariables:}'.split(',')}")
    private List<String> filtervariables;

    @Bean
    public CamundaOutputConfiguration camundaOutputConfiguration() {
        return new CamundaOutputConfiguration(this.filtervariables);
    }

    @Bean
    @ConditionalOnMissingBean
    public OutputService outputService(final Sinks.Many<Message<Map<String, Object>>> dynamicSink) {
        return new OutputServiceImpl(dynamicSink);
    }

    @Bean
    @ExternalTaskSubscription("generic-output")
    public CamundaOutputClient camundaOutputClient(final OutputService outputService, final CamundaOutputConfiguration camundaOutputConfiguration, final EngineDataSerializer engineDataSerializer) {
        return new CamundaOutputClient(outputService, camundaOutputConfiguration, engineDataSerializer);
    }

}
