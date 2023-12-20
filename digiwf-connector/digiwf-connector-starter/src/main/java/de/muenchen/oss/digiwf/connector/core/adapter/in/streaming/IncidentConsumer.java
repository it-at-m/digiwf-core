package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateIncidentInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Generic Listener to create incidents in a process instance.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IncidentConsumer {

    private static final String HEADER_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";
    private static final String HEADER_MESSAGE_NAME = "digiwf.messagename";

    private final CreateIncidentInPort inPort;

    @Bean
    public Consumer<Message<String>> createIncident() {
        return correlation -> {
            final Optional<String> processInstanceId = Optional.ofNullable(correlation.getHeaders().get(HEADER_PROCESS_INSTANCE_ID)).map(Object::toString);
            final Optional<String> messageName = Optional.ofNullable(correlation.getHeaders().get(HEADER_MESSAGE_NAME)).map(Object::toString);

            if (processInstanceId.isEmpty()) {
                log.error("No process instance id present. Cannot create an incident");
                return;
            }

            if (messageName.isEmpty()) {
                log.error("No messageName is present. Cannot create an incident");
                return;
            }

            log.info("Received create incident for process instance with id: {}", processInstanceId.get());
            this.inPort.createIncident(processInstanceId.get(), messageName.get(), correlation.getPayload());
        };
    }
}
