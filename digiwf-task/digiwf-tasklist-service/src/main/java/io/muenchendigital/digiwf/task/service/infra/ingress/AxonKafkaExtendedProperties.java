package io.muenchendigital.digiwf.task.service.infra.ingress;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polyflow.axon.kafka")
@Data
public class AxonKafkaExtendedProperties {
    private String topicTasks;
    private String topicDataEntries;
}
