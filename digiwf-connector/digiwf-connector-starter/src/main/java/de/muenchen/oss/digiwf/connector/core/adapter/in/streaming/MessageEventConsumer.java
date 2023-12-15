package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CorrelateMessageInPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
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

    private final CorrelateMessageInPort inPort;

    @Bean
    public Consumer<Message<CorrelateMessageDto>> correlateMessage() {
        return correlation -> {
            log.info("Received message correlation {}", correlation.getPayload());
            this.inPort.correlateMessage(map(correlation.getPayload()));
        };
    }

    private MessageCorrelation map(CorrelateMessageDto event) {
        MessageCorrelation messageCorrelation = new MessageCorrelation();
        messageCorrelation.setProcessInstanceId(event.getProcessInstanceId());
        messageCorrelation.setMessageName(event.getMessageName());
        messageCorrelation.setBusinessKey(event.getBusinessKey());
        messageCorrelation.setPayloadVariables(event.getPayloadVariables());
        messageCorrelation.setPayloadVariablesLocal(event.getPayloadVariablesLocal());
        return messageCorrelation;
    }
}
