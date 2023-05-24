package io.muenchendigital.digiwf.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.camunda.taskpool.api.business.AuthorizationChange;
import io.holunda.camunda.taskpool.api.task.SourceReference;
import io.holunda.polyflow.bus.jackson.DataEntryStateTypeMappingModule;
import io.holunda.polyflow.bus.jackson.KotlinTypeInfo;
import io.holunda.polyflow.bus.jackson.VariableMapTypeMappingModule;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import io.holunda.polyflow.datapool.core.EnablePolyflowDataPool;
import io.holunda.polyflow.taskpool.core.EnablePolyflowTaskPool;
import io.holunda.polyflow.taskpool.sender.SenderConfiguration;
import io.holunda.polyflow.view.filter.Criterion;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.springboot.autoconfig.ObjectMapperAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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
        SenderConfiguration.class
})
@EnableConfigurationProperties(
    TaskManagementProperties.class
)
public class PolyflowConnectorAutoConfiguration {

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
}
