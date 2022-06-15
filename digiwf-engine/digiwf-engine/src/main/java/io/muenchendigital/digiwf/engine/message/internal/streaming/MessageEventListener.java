package io.muenchendigital.digiwf.engine.message.internal.streaming;

import io.muenchendigital.digiwf.engine.message.api.CorrelateMessageDto;
import io.muenchendigital.digiwf.engine.message.internal.impl.service.MessageService;
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

    private final MessageService messageService;
    private final MessageApiMapper messageApiMapper;

    @Bean
    public Consumer<Message<CorrelateMessageDto>> correlateMessageV01() {
        return correlation -> {
            log.info("Received message correlation {}", correlation.getPayload());
            this.messageService.correlateMessage(this.messageApiMapper.map(correlation.getPayload()));
        };
    }
}
