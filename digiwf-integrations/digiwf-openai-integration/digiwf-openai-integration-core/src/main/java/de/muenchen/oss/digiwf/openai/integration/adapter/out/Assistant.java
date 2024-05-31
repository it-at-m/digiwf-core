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
    @UserMessage("Summarize the following text: {{text}} with the following length: {{length}} and don't translate it")
    String summarize(@V("text") String text, @V("length") int length);

    @SystemMessage("Your are a simple mail generator and only return the generated mail without any explanation")
    @UserMessage("Generate a mail from the template: {{template}} with the following json: {{json}} as input for the template placeholders defined by curly braces and translate the mail to the language: {{language}} ")
    String generateMail(@V("json") String json, @V("language") String language, @V("template") String template);

    @SystemMessage("You are a simple json mapper bot and only return the mapped json without any explanation")
    @UserMessage("Map the following json: {{json}} to the following json type: {{type}}")
    String mapJson(@V("json") String json, @V("type") String type);

    @SystemMessage("You are a simple data extractor bot and only return the extracted data as json without any explanation")
    @UserMessage("Extract from the following json: {{json}} the following fields: {{fields}}")
    String extractData(@V("json") String json, @V("fields") String fields);

    @SystemMessage("You are a simple bot for classifying data and only return the classification option without any explanation")
    @UserMessage("Classify the following json: {{json}} and with the following options: {{options}}")
    String classify(@V("json") String json, @V("options") String options);

}