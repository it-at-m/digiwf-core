package de.muenchen.oss.digiwf.openai.integration.application.usecase;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenAiUseCase implements OpenAiInPort {

    private final OpenAiClientOutPort openAiClientOutPort;

    @Override
    public OpenAiResponse chat(final OpenAiRequest openAiRequest) throws BpmnError, IncidentError {
        return openAiClientOutPort.chat(openAiRequest);
    }

}
