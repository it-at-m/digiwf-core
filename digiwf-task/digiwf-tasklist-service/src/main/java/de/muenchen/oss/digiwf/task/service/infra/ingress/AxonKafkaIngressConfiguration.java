package de.muenchen.oss.digiwf.task.service.infra.ingress;

import io.micrometer.core.instrument.MeterRegistry;
import org.axonframework.extensions.kafka.KafkaProperties;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
import org.axonframework.extensions.kafka.eventhandling.consumer.DefaultConsumerFactory;
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.KafkaEventMessage;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.SortedKafkaMessageBuffer;
import org.axonframework.extensions.kafka.eventhandling.consumer.streamable.StreamableKafkaMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ConditionalOnProperty(value = "polyflow.axon.kafka.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(AxonKafkaExtendedProperties.class)
public class AxonKafkaIngressConfiguration {
  @Value("${HOSTNAME:localhost}")
  private String hostname;

  /**
   * Consumer factory for tasks.
   *
   * @param properties kafka properties
   * @return consumer factory.
   */
  @Bean
  @Qualifier("polyflowTask")
  public ConsumerFactory<String, byte[]> kafkaConsumerFactoryPolyflowTask(KafkaProperties properties) {
    properties.setClientId("polyflow-task-" + hostname);
    return new DefaultConsumerFactory<>(properties.buildConsumerProperties());
  }

  /**
   * Consumer factory for data entries.
   *
   * @param properties kafka properties
   * @return consumer factory.
   */
  @Bean
  @Qualifier("polyflowData")
  public ConsumerFactory<String, byte[]> kafkaConsumerFactoryPolyflowData(KafkaProperties properties) {
    properties.setClientId("polyflow-data-" + hostname);
    return new DefaultConsumerFactory<>(properties.buildConsumerProperties());
  }

  /**
   * Creates a streamable kafka message source.
   * The name of this bean is referenced in the application.yaml.
   *
   * @param kafkaProperties      standard kafka properties.
   * @param extendedProperties   extended properties for Polyflow.
   * @param kafkaConsumerFactory consumer factory.
   * @param kafkaFetcher         fetcher instance.
   * @param serializer           serializer.
   * @param meterRegistry        meter registry.
   * @return streaming source.
   */
  @Bean("kafkaMessageSourcePolyflowData")
  @ConditionalOnProperty(value = "axon.kafka.consumer.event-processor-mode", havingValue = "TRACKING")
  public StreamableKafkaMessageSource<String, byte[]> kafkaMessageSourcePolyflowData(
      KafkaProperties kafkaProperties,
      AxonKafkaExtendedProperties extendedProperties,
      @Qualifier("polyflowData") ConsumerFactory<String, byte[]> kafkaConsumerFactory,
      Fetcher<String, byte[], KafkaEventMessage> kafkaFetcher,
      @Qualifier("eventSerializer")
      Serializer serializer,
      KafkaMessageConverter<String, byte[]> messageConverter,
      MeterRegistry meterRegistry) {
    return StreamableKafkaMessageSource
        .<String, byte[]>builder()
        .topics(Collections.singletonList(extendedProperties.getTopicDataEntries()))
        .consumerFactory(new MetricsBindingConsumerFactory<>(meterRegistry, kafkaConsumerFactory))
        .consumerFactory(kafkaConsumerFactory)
        .serializer(serializer)
        .fetcher(kafkaFetcher)
        .messageConverter(messageConverter)
        .bufferFactory(() -> new SortedKafkaMessageBuffer<>(kafkaProperties.getFetcher().getBufferSize()))
        .build();
  }

  /**
   * Creates a streamable kafka message source.
   * The name of this bean is referenced in the application.yaml.
   *
   * @param kafkaProperties      standard kafka properties.
   * @param extendedProperties   extended properties for Polyflow.
   * @param kafkaConsumerFactory consumer factory.
   * @param kafkaFetcher         fetcher instance.
   * @param serializer           serializer.
   * @param meterRegistry        meter registry.
   * @return streaming source.
   */
  @Bean("kafkaMessageSourcePolyflowTask")
  @ConditionalOnProperty(value = "axon.kafka.consumer.event-processor-mode", havingValue = "TRACKING")
  public StreamableKafkaMessageSource<String, byte[]> kafkaMessageSourcePolyflowTask(
      KafkaProperties kafkaProperties,
      AxonKafkaExtendedProperties extendedProperties,
      @Qualifier("polyflowTask") ConsumerFactory<String, byte[]> kafkaConsumerFactory,
      Fetcher<String, byte[], KafkaEventMessage> kafkaFetcher,
      @Qualifier("eventSerializer")
      Serializer serializer,
      KafkaMessageConverter<String, byte[]> messageConverter,
      MeterRegistry meterRegistry) {
    return StreamableKafkaMessageSource
        .<String, byte[]>builder()
        .topics(Collections.singletonList(extendedProperties.getTopicTasks()))
        .consumerFactory(new MetricsBindingConsumerFactory<>(meterRegistry, kafkaConsumerFactory))
        .consumerFactory(kafkaConsumerFactory)
        .serializer(serializer)
        .fetcher(kafkaFetcher)
        .messageConverter(messageConverter)
        .bufferFactory(() -> new SortedKafkaMessageBuffer<>(kafkaProperties.getFetcher().getBufferSize()))
        .build();
  }

}
