package de.muenchen.oss.digiwf.address.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Component
@Slf4j
public class TestMessageConsumer {

    private final Map<String, Map<String, Object>> receivedMessages = new HashMap<>();

    @Bean
    public Consumer<Message<Map<String, Object>>> integrationTestConsumer() {
        return message -> {
            log.info("TestMessageConsumer::message: {}", message);
            final Map<String, Object> payloadVariables = (Map<String, Object>) message.getPayload().get("payloadVariables");
            receivedMessages.put(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(), payloadVariables);
        };
    }

    public Map<String, Object> receiveMessage(final String processInstanceId) {
        return receivedMessages.get(processInstanceId);
    }

}
