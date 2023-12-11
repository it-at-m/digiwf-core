package de.muenchen.oss.digiwf.connector.adapter.camunda.rest;

import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in.CamundaClient;
import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in.CamundaClientConfiguration;
import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.adapter.out.EventEmitterAdapter;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
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
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.connector.adapter.camunda.rest")
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
    public CamundaClient camundaOutputClient(final ExecuteTaskInPort executeTaskInPort, final CamundaClientConfiguration camundaOutputConfiguration, final EngineDataSerializer engineDataSerializer) {
        return new CamundaClient(executeTaskInPort, camundaOutputConfiguration, engineDataSerializer);
    }

}
