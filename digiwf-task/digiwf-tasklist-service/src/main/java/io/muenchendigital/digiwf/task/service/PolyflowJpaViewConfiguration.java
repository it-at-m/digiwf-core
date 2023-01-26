package io.muenchendigital.digiwf.task.service;


import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnablePolyflowJpaView
@EntityScan(basePackageClasses = {
        TokenEntry.class, DeadLetterEntry.class
})
public class PolyflowJpaViewConfiguration {

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
