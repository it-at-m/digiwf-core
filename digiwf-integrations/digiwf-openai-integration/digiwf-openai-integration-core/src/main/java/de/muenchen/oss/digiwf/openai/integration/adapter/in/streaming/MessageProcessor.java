package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.IntegrationOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class MessageProcessor {

    private final OpenAiInPort openAiInPort;
    private final IntegrationOutPort integrationOutPort;

    private final OpenAiMapper openAiMapper;

    private static final String RESPONSE = "response";

    public Consumer<Message<PromptDto>> basicChat() {
        return message -> {
            try {
                log.debug(message.toString());
                final OpenAiRequest model = this.openAiMapper.dto2Model(message.getPayload());
                final OpenAiResponse result = this.openAiInPort.chat(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }
}
