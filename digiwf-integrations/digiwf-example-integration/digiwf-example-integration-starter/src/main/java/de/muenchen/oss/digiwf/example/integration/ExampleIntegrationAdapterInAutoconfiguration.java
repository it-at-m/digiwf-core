package de.muenchen.oss.digiwf.example.integration;

import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleDto;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleMapper;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.StreamingAdapter;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class ExampleIntegrationAdapterInAutoconfiguration {

    @ConditionalOnMissingBean
    @Bean
    public StreamingAdapter messageProcessor(
            final ErrorApi errorApi,
            final ProcessApi processApi,
            final ExampleInPort exampleInPort,
            final ExampleMapper exampleMapper
    ) {
        return new StreamingAdapter(errorApi, processApi, exampleInPort, exampleMapper);
    }

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.exampleIntegration();
    }
}
