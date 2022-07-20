package io.muenchendigital.digiwf.taskanaconnector.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class KafkaTaskProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTaskProducer.class);
    private KafkaTemplate<String, String> digiWfKafkaTemplate;

    private String outgoingTopic;

    public KafkaTaskProducer(String outgoingTopic) {

        this.outgoingTopic = outgoingTopic;

        KafkaProperties kafkaProperties = new KafkaProperties();
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
  
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);
        this.digiWfKafkaTemplate = new KafkaTemplate<>(producerFactory);
    }
   
    public void sendMessage(String message) {
        if (message != null && digiWfKafkaTemplate != null) {
            digiWfKafkaTemplate.send(outgoingTopic, message);
        } else {
            logger.error("Message or KafkaTemplate is null");
        }
    }
}
