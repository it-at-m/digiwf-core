package de.muenchen.oss.digiwf.example.integration.core.adapter.in.streaming;

import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ExampleInPort;
import de.muenchen.oss.digiwf.example.integration.core.application.port.in.ProcessResponseInPort;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessResponseInPort processResponseInPort;
    private final ExampleInPort exampleInPort;
    private final ExampleMapper exampleMapper;

    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> withErrorHandling(message, () -> {
            final ExampleDto exampleDto = message.getPayload();
            this.exampleInPort.processExampleData(this.exampleMapper.toModel(exampleDto));

            this.processResponseInPort.correlateMessage(
                    message.getHeaders(),
                    Map.of("someData", exampleDto.getSomeData())
            );
        });
    }

    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            processResponseInPort.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final IncidentError incidentError) {
            processResponseInPort.handleIncident(message.getHeaders(), incidentError);
        } catch (final ValidationException validationException) {
            processResponseInPort.handleIncident(message.getHeaders(), new IncidentError(validationException.getMessage()));
        }
    }
}
