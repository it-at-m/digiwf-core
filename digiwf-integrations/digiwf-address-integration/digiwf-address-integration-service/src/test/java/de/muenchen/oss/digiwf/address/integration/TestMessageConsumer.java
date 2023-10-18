package de.muenchen.oss.digiwf.address.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.TYPE;

@Configuration
public class TestMessageConsumer {

    private final CountDownLatch latch = new CountDownLatch(1);

    private final Map<String, Map<String, Object>> receivedMessages = new HashMap<>();

    @Bean
    public Consumer<Message<Map<String, Object>>> consume() {
        return message -> {
            receivedMessages.put(message.getHeaders().get(TYPE).toString(), message.getPayload());
            latch.countDown();
        };
    }

    public Map<String, Object> awaitMessage(final String type) throws InterruptedException {
        latch.await(10, TimeUnit.SECONDS);
        return receivedMessages.get(type);
    }

}
