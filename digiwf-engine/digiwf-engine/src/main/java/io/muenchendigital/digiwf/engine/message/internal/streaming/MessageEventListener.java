package io.muenchendigital.digiwf.engine.message.internal.streaming;

import io.muenchendigital.digiwf.engine.message.internal.impl.model.CorrelateMessageImpl;
import io.muenchendigital.digiwf.engine.message.internal.impl.service.MessageServiceImpl;
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
public class MessageEventListener {

    private final MessageServiceImpl messageService;

    @Bean
    public Consumer<Message<CorrelateMessageImpl>> correlateMessageV01() {
        return correlation -> {
            log.info("Received message correlation {}", correlation.getPayload());
            this.messageService.correlateMessage(correlation.getPayload());
        };
    }
}
