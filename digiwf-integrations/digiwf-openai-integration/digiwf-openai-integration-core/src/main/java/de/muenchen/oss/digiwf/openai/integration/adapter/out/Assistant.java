package de.muenchen.oss.digiwf.openai.integration.adapter.out;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

//https://learn.microsoft.com/de-de/azure/ai-services/openai/reference#chat-completions
@AiService(chatModel = "gpt-3.5-turbo")
public interface Assistant {

    @SystemMessage("You are a polite assistant")
    String chat(String userMessage);
}