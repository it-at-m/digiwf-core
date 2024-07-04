package de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

@RequiredArgsConstructor
@Slf4j
public class StreamingAdapter {

    private final ErrorApi errorApi;
    private final ProcessApi processApi;
    private final ExampleInPort exampleInPort;
    private final ExampleMapper exampleMapper;

    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> withErrorHandling(message, () -> {
            final ExampleDto exampleDto = message.getPayload();
            this.exampleInPort.processExampleData(this.exampleMapper.toModel(exampleDto));

            this.correlateMessage(
                    message.getHeaders(),
                    Map.of("someData", exampleDto.getSomeData())
            );
        });
    }

    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            errorApi.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final IncidentError incidentError) {
            errorApi.handleIncident(message.getHeaders(), incidentError);
        } catch (final ValidationException validationException) {
            errorApi.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
        }
    }

    private void correlateMessage(final Map<String, Object> originMessageHeaders, final Map<String, Object> message) {
        String processInstanceId = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID).toString());
        String type = Objects.requireNonNull(originMessageHeaders.get(TYPE).toString());
        String integrationName = Objects.requireNonNull(originMessageHeaders.get(DIGIWF_INTEGRATION_NAME).toString());
        log.info("sending response message for process {}: {}", processInstanceId, message);
        this.processApi.correlateMessage(processInstanceId, type, integrationName, message);
    }
}
