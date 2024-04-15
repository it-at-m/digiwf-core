package de.muenchen.oss.digiwf.openai.integration.application.port.out;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiRequest;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.dto.OpenAiResponse;

/**
 * Port to integration infrastructure.
 */
public interface OpenAiClientOutPort {

    /**
     * Sends a chat request to the OpenAI API.
     * @param openAiRequest
     * @return
     * @throws BpmnError
     * @throws IncidentError
     */
    OpenAiResponse chat(final OpenAiRequest openAiRequest) throws BpmnError, IncidentError;

}
