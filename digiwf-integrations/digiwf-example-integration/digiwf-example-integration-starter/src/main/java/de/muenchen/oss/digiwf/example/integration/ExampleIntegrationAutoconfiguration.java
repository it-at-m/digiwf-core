package de.muenchen.oss.digiwf.example.integration;


import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleDto;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.ExampleMapper;
import de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.example.integration.core.adapter.out.streaming.ProcessAdapter;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ProcessResponseInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ProcessResponseOutPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"de.muenchen.oss.digiwf.example.integration.core"})
public class ExampleIntegrationAutoconfiguration {
    @Bean
    public ProcessResponseOutPort processAdapter(final ProcessApi processApi, final ErrorApi errorApi) {
        return new ProcessAdapter(processApi, errorApi);
    }

    @Bean
    public MessageProcessor messageProcessor(
            ProcessResponseInPort processResponseInPort,
            ExampleInPort exampleInPort,
            ExampleMapper exampleMapper
    ) {
        return new MessageProcessor(processResponseInPort, exampleInPort, exampleMapper);
    }

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration(final MessageProcessor messageProcessor) {
        return messageProcessor.exampleIntegration();
    }
}
