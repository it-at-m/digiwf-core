package io.muenchendigital.digiwf.message.api.streaming;

import io.muenchendigital.digiwf.asyncapi.docs.annotations.DocumentAsyncAPI;
import io.muenchendigital.digiwf.message.domain.service.MessageService;
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

    @DocumentAsyncAPI(payload = CorrelateMessageTOV01.class, functionRouter = true, typeHeader = "correlateMessageV01")
    @Bean
    public Consumer<Message<CorrelateMessageTOV01>> correlateMessageV01() {
        return correlation -> {
            log.info("Received message correlation {}", correlation.getPayload());
            this.messageService.correlateMessage(this.messageApiMapper.map(correlation.getPayload()));
        };
    }
}
