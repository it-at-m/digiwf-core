package de.muenchen.oss.digiwf.address.integration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
public class EmbeddedKafkaConfig {

    @Bean
    @ConditionalOnMissingBean
    public EmbeddedKafkaBroker embeddedKafkaConfig() {
        return new EmbeddedKafkaBroker(
                1,
                true,
                "dwf-address-e2e-test",
                "dwf-connector-e2e-test",
                "dwf-connector-bpmnerror-e2e-test",
                "dwf-connector-incident-e2e-test"
        );
    }

}
