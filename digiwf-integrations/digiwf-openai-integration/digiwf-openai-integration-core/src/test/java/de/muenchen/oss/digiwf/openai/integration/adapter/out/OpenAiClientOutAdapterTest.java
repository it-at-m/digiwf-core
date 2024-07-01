package de.muenchen.oss.digiwf.openai.integration.adapter.out;

import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.ai.Assistant;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.ai.OpenAiClientOutAdapter;
import de.muenchen.oss.digiwf.openai.integration.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OpenAiClientOutAdapterTest {

    private final Assistant assistant = Mockito.mock(Assistant.class);

    private final OpenAiClientOutAdapter openAiClientOutAdapter = new OpenAiClientOutAdapter(this.assistant);

    @Test
    void chat() {
        when(this.assistant.chat("test")).thenReturn("answer");

        final OpenAiResponse result = this.openAiClientOutAdapter.chat(new ChatRequest("test"));

        assertEquals("answer", result.getAnswer());
        Mockito.verify(this.assistant).chat("test");

        Mockito.reset(this.assistant);

        when(this.assistant.chat("test")).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.chat(new ChatRequest("test")), "error");
        Mockito.verify(this.assistant).chat("test");
    }

    @Test
    void translate() {
        when(this.assistant.translate("test", "de")).thenReturn("answer");

        final OpenAiResponse result = this.openAiClientOutAdapter.translate(new TranslateRequest("test", "de"));

        assertEquals("answer", result.getAnswer());
        Mockito.verify(this.assistant).translate("test", "de");

        Mockito.reset(this.assistant);

        when(this.assistant.translate("test", "de")).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.translate(new TranslateRequest("test", "de")), "error");
        Mockito.verify(this.assistant).translate("test", "de");
    }

    @Test
    void summarize() {
        when(this.assistant.summarize("test", 2)).thenReturn("te");

        final OpenAiResponse result = this.openAiClientOutAdapter.summarize(new SummarizeRequest("test", 2));

        assertEquals("te", result.getAnswer());
        Mockito.verify(this.assistant).summarize("test", 2);

        Mockito.reset(this.assistant);

        when(this.assistant.summarize("test", 2)).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.summarize(new SummarizeRequest("test", 2)), "error");
        Mockito.verify(this.assistant).summarize("test", 2);
    }

    @Test
    void generateMail() {
        when(this.assistant.generateMail("{\"test\": \"test\"}", "de", "Hallo {{test}}")).thenReturn("Hallo test");

        final OpenAiResponse result = this.openAiClientOutAdapter.generateMail(new GenerateMailRequest("{\"test\": \"test\"}", "de", "Hallo {{test}}"));

        assertEquals("Hallo test", result.getAnswer());
        Mockito.verify(this.assistant).generateMail("{\"test\": \"test\"}", "de", "Hallo {{test}}");

        Mockito.reset(this.assistant);

        when(this.assistant.generateMail("{\"test\": \"test\"}", "de", "Hallo {{test}}")).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.generateMail(new GenerateMailRequest("{\"test\": \"test\"}", "de", "Hallo {{test}}")), "error");
        Mockito.verify(this.assistant).generateMail("{\"test\": \"test\"}", "de", "Hallo {{test}}");
    }

    @Test
    void extractData() {
        when(this.assistant.extractData("{\"test\": \"test\"}", "test")).thenReturn("{\"test\": \"test\"}");

        final OpenAiResponse result = this.openAiClientOutAdapter.extractData(new ExtractDataRequest("{\"test\": \"test\"}", "test"));

        assertEquals("{\"test\": \"test\"}", result.getAnswer());
        Mockito.verify(this.assistant).extractData("{\"test\": \"test\"}", "test");

        Mockito.reset(this.assistant);

        when(this.assistant.extractData("{\"test\": \"test\"}", "test")).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.extractData(new ExtractDataRequest("{\"test\": \"test\"}", "test")), "error");
        Mockito.verify(this.assistant).extractData("{\"test\": \"test\"}", "test");
    }

    @Test
    void classify() {
        when(this.assistant.classify("{\"test\": \"test\"}", "test, nope")).thenReturn("test");

        final OpenAiResponse result = this.openAiClientOutAdapter.classify(new ClassifyRequest("{\"test\": \"test\"}", "test, nope"));

        assertEquals("test", result.getAnswer());
        Mockito.verify(this.assistant).classify("{\"test\": \"test\"}", "test, nope");

        Mockito.reset(this.assistant);

        when(this.assistant.classify("{\"test\": \"test\"}", "test, nope")).thenThrow(new RuntimeException("error"));
        Assertions.assertThrows(IncidentError.class, () -> this.openAiClientOutAdapter.classify(new ClassifyRequest("{\"test\": \"test\"}", "test, nope")), "error");
        Mockito.verify(this.assistant).classify("{\"test\": \"test\"}", "test, nope");
    }
}