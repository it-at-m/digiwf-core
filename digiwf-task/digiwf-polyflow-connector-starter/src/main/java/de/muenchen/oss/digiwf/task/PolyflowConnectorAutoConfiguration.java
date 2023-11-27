package de.muenchen.oss.digiwf.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import io.holunda.polyflow.datapool.core.EnablePolyflowDataPool;
import io.holunda.polyflow.taskpool.collector.CamundaTaskpoolCollectorConfiguration;
import io.holunda.polyflow.taskpool.core.EnablePolyflowTaskPool;
import io.holunda.polyflow.taskpool.sender.SenderConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.springboot.autoconfig.ObjectMapperAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Client integration components

// Enable TaskPool and DataPool Core Components
@EnablePolyflowTaskPool
@EnablePolyflowDataPool

@Configuration
@ComponentScan
@AutoConfigureBefore(value = {
        AxonAutoConfiguration.class,
        ObjectMapperAutoConfiguration.class,
        FallbackPayloadObjectMapperAutoConfiguration.class,
        SenderConfiguration.class,
        CamundaTaskpoolCollectorConfiguration.class
})
@EnableConfigurationProperties(
    TaskManagementProperties.class
)
@Slf4j
public class PolyflowConnectorAutoConfiguration {

    @PostConstruct
    public void post() {
        log.info("Loaded Polyflow configuration.");
    }

    /**
     * Provides an objectmapper for Polyflow payload serialization.
     * @return objectmapper.
     */
    @Bean
    @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
    public ObjectMapper payloadObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

    /**
     * Provides an objectmapper for Axon message serialization.
     * @return objectmapper.
     */
    @Bean("defaultAxonObjectMapper")
    @Qualifier("defaultAxonObjectMapper")
    public ObjectMapper defaultAxonObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

    /**
     * Expose assignment properties as bean.
     * @param properties task management properties.
     * @return assignment part of the properties.
     */
    @Bean
    public TaskManagementProperties.AssignmentProperties assignmentProperties(TaskManagementProperties properties) {
        return properties.getAssignment();
    }

    @Bean
    public EventStorageEngine jpaStorageEngine(
            EntityManagerProvider entityManagerProvider,
            TransactionManager transactionManager,
            @Qualifier("eventSerializer")
            Serializer eventSerializer
    ) {
        return JpaEventStorageEngine
                .builder()
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(transactionManager)
                .eventSerializer(eventSerializer)
                .build();
    }
}
