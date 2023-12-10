package de.muenchen.oss.digiwf.camunda.connector.configuration;

import de.muenchen.oss.digiwf.camunda.connector.adapter.EngineDataSerializer;
import de.muenchen.oss.digiwf.camunda.connector.adapter.in.CamundaClient;
import de.muenchen.oss.digiwf.camunda.connector.adapter.in.CamundaClientConfiguration;
import de.muenchen.oss.digiwf.connector.adapter.out.EventEmitterAdapter;
import de.muenchen.oss.digiwf.connector.application.port.out.EmitEventOutPort;
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
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.camunda.connector")
@EnableConfigurationProperties(DigiWFCamundaConnectorProperties.class)
public class DigiWFCamundaConnectorAutoConfiguration {


    @Value("#{'${io.muenchendigital.digiwf.camunda.connector.filtervariables:}'.split(',')}")
    private List<String> filtervariables;

    @Bean
    public CamundaClientConfiguration camundaOutputConfiguration() {
        return new CamundaClientConfiguration(this.filtervariables);
    }

    @Bean
    @ConditionalOnMissingBean
    public EmitEventOutPort outputService(final Sinks.Many<Message<Map<String, Object>>> dynamicSink) {
        return new EventEmitterAdapter(dynamicSink);
    }

    @Bean
    @ExternalTaskSubscription("generic-output")
    public CamundaClient camundaOutputClient(final EmitEventOutPort outputService, final CamundaClientConfiguration camundaOutputConfiguration, final EngineDataSerializer engineDataSerializer) {
        return new CamundaClient(outputService, camundaOutputConfiguration, engineDataSerializer);
    }

}
