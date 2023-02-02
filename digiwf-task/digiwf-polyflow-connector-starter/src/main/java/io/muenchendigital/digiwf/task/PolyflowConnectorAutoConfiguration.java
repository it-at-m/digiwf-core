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
import org.axonframework.springboot.autoconfig.ObjectMapperAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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
        ObjectMapperAutoConfiguration.class,
        FallbackPayloadObjectMapperAutoConfiguration.class,
        SenderConfiguration.class
})
public class PolyflowConnectorAutoConfiguration {

    @Bean
    @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
    public ObjectMapper payloadObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

    @Bean
    @Qualifier("defaultAxonObjectMapper")
    public ObjectMapper axonObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }


    // FIXME: understand why that object mapper is relevant at all...

    /**
     * Configure default object mapper using the customizer.
     *
     * @return customizer.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder
                .modules(
                        new VariableMapTypeMappingModule(),
                        new DataEntryStateTypeMappingModule()
                )
                .mixIn(SourceReference.class, KotlinTypeInfo.class)
                .mixIn(AuthorizationChange.class, KotlinTypeInfo.class)
                .mixIn(Criterion.class, KotlinTypeInfo.class);
    }
}
