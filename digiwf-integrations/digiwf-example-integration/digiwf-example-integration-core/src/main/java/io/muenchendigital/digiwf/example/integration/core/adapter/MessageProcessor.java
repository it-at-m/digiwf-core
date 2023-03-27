package io.muenchendigital.digiwf.example.integration.core.adapter;

import io.muenchendigital.digiwf.example.integration.core.application.in.ExampleUseCase;
import io.muenchendigital.digiwf.example.integration.core.application.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor  implements CorrelateMessagePort {

    private final ProcessApi processApi;
    private final ExampleUseCase exampleUseCase;
    private final ExampleMapper exampleMapper;

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            final ExampleDto exampleDto = message.getPayload();
            this.exampleUseCase.processExampleData(this.exampleMapper.toModel(exampleDto));

            this.correlateMessage(exampleDto.getProcessInstanceId(), "exampleMessage", Map.of("someData", exampleDto.getSomeData()));
        };
    }

    @Override
    public void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message) {
        this.processApi.correlateMessage(processInstanceId, messageName, message);
    }

}
