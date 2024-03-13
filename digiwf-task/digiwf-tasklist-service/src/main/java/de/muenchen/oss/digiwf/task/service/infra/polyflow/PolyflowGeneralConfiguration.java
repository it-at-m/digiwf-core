package de.muenchen.oss.digiwf.task.service.infra.polyflow;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.task.PolyflowObjectMapper;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnablePolyflowJpaView
public class PolyflowGeneralConfiguration {


  @Bean
  @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
  public ObjectMapper payloadObjectMapper() {
    return PolyflowObjectMapper.DEFAULT;
  }



  /**
   * Initializes the client with the query gateway.
   *
   * @param queryGateway gateway to use.
   * @return client.
   */
  @Bean
  public TaskQueryClient taskQueryClient(QueryGateway queryGateway) {
    return new TaskQueryClient(queryGateway);
  }

}
