package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.ClassifyDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.ExtractDataDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.GenerateMailDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.SummarizeDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.TranslateDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ChatRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ClassifyRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ExtractDataRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.GenerateMailRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.SummarizeRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.TranslateRequest;
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
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ChatRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.chat(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<TranslateDto>> translate() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final TranslateRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.translate(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<SummarizeDto>> summarize() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final SummarizeRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.summarize(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<GenerateMailDto>> generateMail() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final GenerateMailRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.generateMail(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<ExtractDataDto>> extractData() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ExtractDataRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.extractData(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }

    public Consumer<Message<ClassifyDto>> classify() {
        return message -> withErrorHandling(message, () -> {
            log.debug(message.toString());
            final ClassifyRequest model = this.openAiMapper.dto2Model(message.getPayload());
            final OpenAiResponse result = this.openAiInPort.classify(model);
            this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result.getAnswer()));
        });
    }


    private void withErrorHandling(final Message<?> message, final Runnable runnable) {
        try {
            runnable.run();
        } catch (final BpmnError bpmnError) {
            this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
        } catch (final IncidentError incidentError) {
            this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
        }
    }
}
