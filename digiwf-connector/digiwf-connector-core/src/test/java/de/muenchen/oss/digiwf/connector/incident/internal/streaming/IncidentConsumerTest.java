package de.muenchen.oss.digiwf.connector.incident.internal.streaming;

import de.muenchen.oss.digiwf.connector.api.incident.IncidentService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@DisplayName("Incident Consumer Test")
class IncidentConsumerTest {
    private static final String HEADER_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";
    private static final String HEADER_MESSAGE_NAME = "digiwf.messagename";
    private final IncidentService incidentService = mock(IncidentService.class);

    private final IncidentConsumer incidentConsumer = new IncidentConsumer(incidentService);

    @Test
    @DisplayName("should do noting if headers are empty")
    public void shouldDoNothingIfHeadersAreEmpty() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public @NotNull String getPayload() {
                return "payload";
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        });

        verifyNoInteractions(incidentService); //.createIncident(anyString(), anyString(), anyString()));
    }

    @Test
    @DisplayName("should create incident if headers are given")
    public void shouldCreateIncidentIfHeadersAreGiven() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public @NotNull String getPayload() {
                return "payload";
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                final HashMap<String, Object> map = new HashMap<>();
                map.put(HEADER_PROCESS_INSTANCE_ID, "process-instance-id");
                map.put(HEADER_MESSAGE_NAME, "message-name");

                return new MessageHeaders(map);
            }
        });

        verify(incidentService).createIncident("process-instance-id", "message-name", "payload");
    }

    @Test
    @DisplayName("should create incident if payload is missing")
    public void shouldCreateIncidentIfPayloadIsMissing() {
        Consumer<Message<String>> consumer = incidentConsumer.createIncident();

        consumer.accept(new Message<String>() {
            @Override
            public String getPayload() {
                return null;
            }

            @Override
            public @NotNull MessageHeaders getHeaders() {
                final HashMap<String, Object> map = new HashMap<>();
                map.put(HEADER_PROCESS_INSTANCE_ID, "process-instance-id");
                map.put(HEADER_MESSAGE_NAME, "message-name");

                return new MessageHeaders(map);
            }
        });

        verify(incidentService).createIncident("process-instance-id", "message-name", null);
    }
}
