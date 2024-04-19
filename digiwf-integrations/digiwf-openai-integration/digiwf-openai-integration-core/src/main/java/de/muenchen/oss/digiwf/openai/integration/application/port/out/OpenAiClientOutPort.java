package de.muenchen.oss.digiwf.openai.integration.application.port.out;

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

/**
 * Port to integration infrastructure.
 */
public interface OpenAiClientOutPort {

    OpenAiResponse chat(final ChatRequest chatRequest) throws BpmnError, IncidentError;

    OpenAiResponse translate(final TranslateRequest translateRequest) throws BpmnError, IncidentError;

    OpenAiResponse summarize(final SummarizeRequest summarizeRequest) throws BpmnError, IncidentError;

    OpenAiResponse generateMail(final GenerateMailRequest generateMailRequest) throws BpmnError, IncidentError;

    OpenAiResponse mapJson(final MapJsonRequest mapJsonRequest) throws BpmnError, IncidentError;

    OpenAiResponse extractData(final ExtractDataRequest extractDataRequest) throws BpmnError, IncidentError;

    OpenAiResponse classify(final ClassifyRequest classifyRequest) throws BpmnError, IncidentError;

}
