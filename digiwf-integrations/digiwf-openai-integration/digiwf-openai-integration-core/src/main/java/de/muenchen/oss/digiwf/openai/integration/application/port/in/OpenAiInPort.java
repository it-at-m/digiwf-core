package de.muenchen.oss.digiwf.openai.integration.application.port.in;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.domain.*;

/**
 * Port to integration infrastructure.
 */
public interface OpenAiInPort {


    OpenAiResponse chat(final ChatRequest chatRequest) throws BpmnError, IncidentError;

    OpenAiResponse translate(final TranslateRequest translateRequest) throws BpmnError, IncidentError;

    OpenAiResponse summarize(final SummarizeRequest summarizeRequest) throws BpmnError, IncidentError;

    OpenAiResponse generateMail(final GenerateMailRequest generateMailRequest) throws BpmnError, IncidentError;

    OpenAiResponse extractData(final ExtractDataRequest extractDataRequest) throws BpmnError, IncidentError;

    OpenAiResponse classify(final ClassifyRequest classifyRequest) throws BpmnError, IncidentError;

}
