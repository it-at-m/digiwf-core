package de.muenchen.oss.digiwf.openai.integration.application.usecase;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import de.muenchen.oss.digiwf.openai.integration.domain.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenAiUseCase implements OpenAiInPort {

    private final OpenAiClientOutPort openAiClientOutPort;

    @Override
    public OpenAiResponse chat(final ChatRequest chatRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.chat(chatRequest);
    }

    @Override
    public OpenAiResponse translate(TranslateRequest translateRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.translate(translateRequest);
    }

    @Override
    public OpenAiResponse summarize(SummarizeRequest summarizeRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.summarize(summarizeRequest);
    }

    @Override
    public OpenAiResponse generateMail(GenerateMailRequest generateMailRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.generateMail(generateMailRequest);
    }

    @Override
    public OpenAiResponse extractData(ExtractDataRequest extractDataRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.extractData(extractDataRequest);
    }

    @Override
    public OpenAiResponse classify(ClassifyRequest classifyRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.classify(classifyRequest);
    }

}
