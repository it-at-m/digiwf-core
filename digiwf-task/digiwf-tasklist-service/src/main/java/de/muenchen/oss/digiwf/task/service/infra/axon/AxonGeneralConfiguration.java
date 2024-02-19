package de.muenchen.oss.digiwf.task.service.infra.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.task.PolyflowObjectMapper;
import org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackageClasses = {
    TokenEntry.class,
    DeadLetterEntry.class
})
public class AxonGeneralConfiguration {
    /**
     * Provides an object mapper for Axon message serialization.
     *
     * @return object mapper.
     */
    @Bean("defaultAxonObjectMapper")
    @Qualifier("defaultAxonObjectMapper")
    public ObjectMapper defaultAxonObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

    /**
     * We will receive events via Kafka, so no event storage is available in this component.
     *
     * @return in-memory storage engine, to make Axon Framework happy.
     */
    @Bean
    public EventStorageEngine inMemoryStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    /**
     * No sagas should be handled in this component.
     *
     * @return in-memory saga-store to make Axon Framework happy.
     */
    @Bean
    public SagaStore<?> inMemorySagaStore() {
        return new InMemorySagaStore();
    }

}
