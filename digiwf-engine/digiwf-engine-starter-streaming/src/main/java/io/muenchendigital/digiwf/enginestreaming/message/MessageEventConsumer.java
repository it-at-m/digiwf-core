package io.muenchendigital.digiwf.enginestreaming.message;

import io.muenchendigital.digiwf.engine.message.api.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Generic Listener to correlate to processes.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventConsumer {

    private final MessageService messageService;

    @Bean
    public Consumer<Message<CorrelateMessageDto>> correlateMessageV01() {
        return correlation -> {
            log.info("Received message correlation {}", correlation.getPayload());
            this.messageService.correlateMessage(correlation.getPayload());
        };
    }
}
