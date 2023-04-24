package io.muenchendigital.digiwf.task.service.infra.persistence;

import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnablePolyflowJpaView
@EntityScan(basePackageClasses = {
    TokenEntry.class,
    DeadLetterEntry.class
})
public class PolyflowPersistenceConfiguration {
}
