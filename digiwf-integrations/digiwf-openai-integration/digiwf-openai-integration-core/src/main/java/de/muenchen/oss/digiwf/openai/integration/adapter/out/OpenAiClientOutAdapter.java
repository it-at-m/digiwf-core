package de.muenchen.oss.digiwf.openai.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenAiClientOutAdapter implements OpenAiClientOutPort {

    Assistant assistant;

    OpenAiClientOutAdapter(Assistant assistant) {
        this.assistant = assistant;
    }


    @Override
    public OpenAiResponse chat(final OpenAiRequest openAiRequest) throws BpmnError, IncidentError {
        try {
            return new OpenAiResponse(this.assistant.chat(openAiRequest.getPrompt()));
        } catch (final Exception exception) {
            throw new IncidentError(exception.getMessage());
        }
    }

}
