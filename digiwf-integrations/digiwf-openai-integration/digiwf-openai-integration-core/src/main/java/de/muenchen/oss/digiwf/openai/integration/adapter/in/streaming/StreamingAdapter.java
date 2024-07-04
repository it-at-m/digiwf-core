package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class StreamingAdapter {

    private static final String RESPONSE = "response";
    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final OpenAiInPort openAiInPort;
    private final OpenAiMapper openAiMapper;

    public Consumer<Message<PromptDto>> basicChat() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ChatRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.chat(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<TranslateDto>> translate() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final TranslateRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.translate(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<SummarizeDto>> summarize() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final SummarizeRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.summarize(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<GenerateMailDto>> generateMail() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final GenerateMailRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.generateMail(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<ExtractDataDto>> extractData() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ExtractDataRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.extractData(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<ClassifyDto>> classify() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ClassifyRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.classify(model);
            this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }


    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final IncidentError incidentError) {
            this.errorApi.handleIncident(message.getHeaders(), incidentError);
        }
    }

    public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
        final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
        final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
        final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
        if (payload == null) {
            payload = new HashMap<>();
        }
        this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
    }
}
