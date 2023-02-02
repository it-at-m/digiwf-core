package io.muenchendigital.digiwf.task.polyflow.kafka;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "polyflow.axon.kafka")
@ConstructorBinding
@Value
public class PolyflowAxonKafkaProperties {
    /**
     * List of mappings of payload class to kafka topic name that payload of this class should be directed to.
     */
    List<PayloadTypeToTopic> topics;

    @ConstructorBinding
    @Value
    public static class PayloadTypeToTopic {
        Class<?> payloadType;
        String topic;
    }
}

