package de.muenchen.oss.digiwf.example.integration;

import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleDto;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleMapper;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ProcessResponseInPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class ExampleIntegrationAdapterInAutoconfiguration {

    @ConditionalOnMissingBean
    @Bean
    public MessageProcessor messageProcessor(
            final ProcessResponseInPort processResponseInPort,
            final ExampleInPort exampleInPort,
            final ExampleMapper exampleMapper
    ) {
        return new MessageProcessor(processResponseInPort, exampleInPort, exampleMapper);
    }

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration(final MessageProcessor messageProcessor) {
        return messageProcessor.exampleIntegration();
    }
}
