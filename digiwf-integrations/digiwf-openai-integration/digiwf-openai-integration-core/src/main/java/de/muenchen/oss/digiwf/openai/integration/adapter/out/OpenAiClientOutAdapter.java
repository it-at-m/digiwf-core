package de.muenchen.oss.digiwf.openai.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ChatRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ClassifyRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.ExtractDataRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.GenerateMailRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.MapJsonRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.SummarizeRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.TranslateRequest;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenAiClientOutAdapter implements OpenAiClientOutPort {

    @NonNull
    Assistant assistant;

    @Override
    public OpenAiResponse chat(final ChatRequest chatRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.chat(chatRequest.getPrompt()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse translate(TranslateRequest translateRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.translate(translateRequest.getText(), translateRequest.getLanguage()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse summarize(SummarizeRequest summarizeRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.summarize(summarizeRequest.getText(), summarizeRequest.getLength()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse generateMail(GenerateMailRequest generateMailRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.generateMail(generateMailRequest.getJson(), generateMailRequest.getLanguage(), generateMailRequest.getTemplate()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse mapJson(MapJsonRequest mapJsonRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.mapJson(mapJsonRequest.getJson(), mapJsonRequest.getType()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse extractData(ExtractDataRequest extractDataRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.extractData(extractDataRequest.getJson(), extractDataRequest.getFields()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

    @Override
    public OpenAiResponse classify(ClassifyRequest classifyRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.classify(classifyRequest.getJson(), classifyRequest.getOptions()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

}
