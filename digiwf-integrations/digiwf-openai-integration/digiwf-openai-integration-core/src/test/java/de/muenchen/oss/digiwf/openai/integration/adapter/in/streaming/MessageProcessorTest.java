package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

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
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_INTEGRATION_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_DEFINITION;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class MessageProcessorTest {

    private final OpenAiInPort openAiInPort = Mockito.mock(OpenAiInPort.class);
    private final IntegrationOutPort integrationOutPort = Mockito.mock(IntegrationOutPort.class);
    private final OpenAiMapper openAiMapper = new OpenAiMapperImpl();

    private final MessageProcessor unitUnderTest = new MessageProcessor(openAiInPort, integrationOutPort, openAiMapper);

    private static final String RESPONSE = "response";

    @Test
    void basicChat() {
        final PromptDto promptDto = PromptDto.builder().prompt("this is a test").build();
        when(openAiInPort.chat(any())).thenReturn(new OpenAiResponse("this is an answer"));

        Message<PromptDto> message = new Message<>() {
            @NotNull
            @Override
            public PromptDto getPayload() {
                return promptDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "basicChat", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.basicChat().accept(message);

        Mockito.verify(openAiInPort).chat(new ChatRequest("this is a test"));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.chat(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.basicChat().accept(message);

        Mockito.verify(openAiInPort).chat(new ChatRequest("this is a test"));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }

    @Test
    void translate() {
        final TranslateDto translateDto = TranslateDto.builder().text("this is a test").language("en").build();
        when(openAiInPort.translate(any())).thenReturn(new OpenAiResponse("this is an answer"));

        Message<TranslateDto> message = new Message<>() {
            @NotNull
            @Override
            public TranslateDto getPayload() {
                return translateDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "translate", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.translate().accept(message);

        Mockito.verify(openAiInPort).translate(new TranslateRequest("this is a test", "en"));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.translate(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.translate().accept(message);

        Mockito.verify(openAiInPort).translate(new TranslateRequest("this is a test", "en"));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }

    @Test
    void summarize() {
        final SummarizeDto summarizeDto = SummarizeDto.builder().text("this is a test").length(1).build();
        when(openAiInPort.summarize(any())).thenReturn(new OpenAiResponse("this is an answer"));

        Message<SummarizeDto> message = new Message<>() {
            @NotNull
            @Override
            public SummarizeDto getPayload() {
                return summarizeDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "summarize", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.summarize().accept(message);

        Mockito.verify(openAiInPort).summarize(new SummarizeRequest("this is a test", 1));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.summarize(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.summarize().accept(message);

        Mockito.verify(openAiInPort).summarize(new SummarizeRequest("this is a test", 1));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }

    @Test
    void generateMail() {
        final GenerateMailDto generateMailDto = GenerateMailDto.builder().template("Hallo {name}").json("{\"name\": \"Rene\"}").language("de").build();
        when(openAiInPort.generateMail(any())).thenReturn(new OpenAiResponse("Hallo Rene"));

        Message<GenerateMailDto> message = new Message<>() {
            @NotNull
            @Override
            public GenerateMailDto getPayload() {
                return generateMailDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "generateMail", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.generateMail().accept(message);

        Mockito.verify(openAiInPort).generateMail(new GenerateMailRequest("{\"name\": \"Rene\"}",  "de", "Hallo {name}"));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "Hallo Rene")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.generateMail(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.generateMail().accept(message);

        Mockito.verify(openAiInPort).generateMail(new GenerateMailRequest("{\"name\": \"Rene\"}",  "de", "Hallo {name}"));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }

    @Test
    void extractData() {
        final ExtractDataDto extractDataDto = ExtractDataDto.builder().json("{\"name\": \"Rene\", \"language\": \"de\"}").fields("name").build();
        when(openAiInPort.extractData(any())).thenReturn(new OpenAiResponse("{\"name\": \"Rene\"}"));

        Message<ExtractDataDto> message = new Message<>() {
            @NotNull
            @Override
            public ExtractDataDto getPayload() {
                return extractDataDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "extractData", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.extractData().accept(message);

        Mockito.verify(openAiInPort).extractData(new ExtractDataRequest("{\"name\": \"Rene\", \"language\": \"de\"}", "name"));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "{\"name\": \"Rene\"}")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.extractData(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.extractData().accept(message);

        Mockito.verify(openAiInPort).extractData(new ExtractDataRequest("{\"name\": \"Rene\", \"language\": \"de\"}", "name"));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }

    @Test
    void classify() {
        final ClassifyDto classifyDto = ClassifyDto.builder().json("{\"name\": \"Rene\", \"language\": \"deutsch\"}").options("de, en").build();
        when(openAiInPort.classify(any())).thenReturn(new OpenAiResponse("de"));

        Message<ClassifyDto> message = new Message<>() {
            @NotNull
            @Override
            public ClassifyDto getPayload() {
                return classifyDto;
            }

            @NotNull
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "openaiIntegration", TYPE, "classify", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };

        unitUnderTest.classify().accept(message);

        Mockito.verify(openAiInPort).classify(new ClassifyRequest("{\"name\": \"Rene\", \"language\": \"deutsch\"}", "de, en"));
        Mockito.verify(integrationOutPort).correlateProcessMessage(any(), eq(Map.of(RESPONSE, "de")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);

        Mockito.reset(openAiInPort, integrationOutPort);

        when(openAiInPort.classify(any())).thenThrow(new IncidentError("Some error"));

        unitUnderTest.classify().accept(message);

        Mockito.verify(openAiInPort).classify(new ClassifyRequest("{\"name\": \"Rene\", \"language\": \"deutsch\"}", "de, en"));
        Mockito.verify(integrationOutPort).handleIncident(any(), eq(new IncidentError("Some error")));
        Mockito.verifyNoMoreInteractions(openAiInPort, integrationOutPort);
    }
}