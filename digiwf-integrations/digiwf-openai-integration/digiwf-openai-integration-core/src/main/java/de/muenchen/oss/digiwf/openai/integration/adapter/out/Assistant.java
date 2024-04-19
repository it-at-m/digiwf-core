package de.muenchen.oss.digiwf.openai.integration.adapter.out;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

    @SystemMessage("You are a polite assistant")
    String chat(String userMessage);

    @SystemMessage("You are a professional translator into {{language}}")
    @UserMessage("Translate the following text: {{text}}")
    String translate(@V("text") String text, @V("language") String language);

    @SystemMessage("You are a professional summarizer")
    @UserMessage("Summarize the following text: {{text}} with the following length: {{length}}")
    String summarize(@V("text") String text, @V("length") int length);

    @SystemMessage("Your are a professional mail generator")
    @UserMessage("Generate a mail for the following json: {{json}} with the language: {{language}} and the template: {{template}}")
    String generateMail(@V("json") String json, @V("language") String language, @V("template") String template);

    @SystemMessage("You are a professional json mapper")
    @UserMessage("Map the following json: {{json}} to the following json type: {{type}}")
    String mapJson(@V("json") String json, @V("type") String type);

    @SystemMessage("You are a professional data extractor")
    @UserMessage("Extract from the following json: {{json}} the following fields: {{fields}} and return as json")
    String extractData(@V("json") String json, @V("fields") String fields);

    @SystemMessage("You are a professional classifier")
    @UserMessage("Classify the following json: {{json}} and with the following options: {{options}}")
    String classify(@V("json") String json, @V("options") String options);

}