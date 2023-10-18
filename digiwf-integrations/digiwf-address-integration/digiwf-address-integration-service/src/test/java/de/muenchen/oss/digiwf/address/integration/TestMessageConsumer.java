package de.muenchen.oss.digiwf.address.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

@Configuration
public class TestMessageConsumer {

    private final Map<String, Map<String, Object>> receivedMessages = new HashMap<>();

    @Bean
    public Consumer<Message<Map<String, Object>>> integrationTestConsumer() {
        return message -> {
            final Map<String, Object> payloadVariables = (Map<String, Object>) message.getPayload().get("payloadVariables");
            receivedMessages.put(message.getHeaders().get(DIGIWF_PROCESS_INSTANCE_ID).toString(), payloadVariables);
        };
    }

    public Map<String, Object> receiveMessage(final String processInstanceId) {
        return receivedMessages.get(processInstanceId);
    }

}
