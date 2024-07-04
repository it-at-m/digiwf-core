package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.domain.*;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class StreamingAdapterTest {

    private static final String RESPONSE = "response";
    private final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    private final ErrorApi errorApi = Mockito.mock(ErrorApi.class);
    private final OpenAiInPort openAiInPort = Mockito.mock(OpenAiInPort.class);
    private final OpenAiMapper openAiMapper = new OpenAiMapperImpl();
    private final StreamingAdapter unitUnderTest = new StreamingAdapter(processApi, errorApi, openAiInPort, openAiMapper);

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
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.chat(any())).thenThrow(error);

        unitUnderTest.basicChat().accept(message);

        Mockito.verify(openAiInPort).chat(new ChatRequest("this is a test"));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
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
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.translate(any())).thenThrow(error);

        unitUnderTest.translate().accept(message);

        Mockito.verify(openAiInPort).translate(new TranslateRequest("this is a test", "en"));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
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
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "this is an answer")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.summarize(any())).thenThrow(error);

        unitUnderTest.summarize().accept(message);

        Mockito.verify(openAiInPort).summarize(new SummarizeRequest("this is a test", 1));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
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

        Mockito.verify(openAiInPort).generateMail(new GenerateMailRequest("{\"name\": \"Rene\"}", "de", "Hallo {name}"));
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "Hallo Rene")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.generateMail(any())).thenThrow(error);

        unitUnderTest.generateMail().accept(message);

        Mockito.verify(openAiInPort).generateMail(new GenerateMailRequest("{\"name\": \"Rene\"}", "de", "Hallo {name}"));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
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
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "{\"name\": \"Rene\"}")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.extractData(any())).thenThrow(error);

        unitUnderTest.extractData().accept(message);

        Mockito.verify(openAiInPort).extractData(new ExtractDataRequest("{\"name\": \"Rene\", \"language\": \"de\"}", "name"));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
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
        Mockito.verify(processApi).correlateMessage(any(), any(), any(), eq(Map.of(RESPONSE, "de")));
        Mockito.verifyNoMoreInteractions(openAiInPort, processApi);

        Mockito.reset(openAiInPort, processApi);

        val error = new IncidentError("Some error");
        when(openAiInPort.classify(any())).thenThrow(error);

        unitUnderTest.classify().accept(message);

        Mockito.verify(openAiInPort).classify(new ClassifyRequest("{\"name\": \"Rene\", \"language\": \"deutsch\"}", "de, en"));
        Mockito.verify(errorApi).handleIncident(any(), eq(error));
        Mockito.verifyNoMoreInteractions(openAiInPort, errorApi);
    }
}