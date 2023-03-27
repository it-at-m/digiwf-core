package io.muenchendigital.digiwf.message.example.adapter;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This adapter is used when the application is not running in streaming mode.
 * It will log the message to the console instead of sending it to a message broker.
 */
@Profile("!streaming")
@Component
@Slf4j
public class NoStreamingMessageAdapter implements MessageApi {

    @Override
    public boolean sendMessage(final Object payload, final String destination) {
        return this.sendMessage(payload, Map.of(), destination);
    }

    @Override
    public boolean sendMessage(final Object payload, final Map<String, Object> headers, final String destination) {
        log.info("Message was successfully sent to {}", destination);
        log.info("Message payload was {}", payload);
        return true;
    }
}
