package io.muenchendigital.digiwf.message.example.adapter;

import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This adapter is used when the application is not running in streaming mode.
 * It will log the message to the console instead of sending it to a message broker.
 */
@Profile("!streaming")
@Component
@Slf4j
public class NoStreamingMessageAdapter implements SendMessagePort {

    @Override
    public boolean sendMessage(final Message message, final String destination) {
        log.info("Message was successfully sent to {}", destination);
        log.info("Message payload was {}", message.getPayload().toString());
        return true;
    }
}
