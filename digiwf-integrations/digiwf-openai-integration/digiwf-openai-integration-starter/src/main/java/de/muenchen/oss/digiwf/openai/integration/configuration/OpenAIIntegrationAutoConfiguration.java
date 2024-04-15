package de.muenchen.oss.digiwf.openai.integration.configuration;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.OpenAiMapper;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.OpenAiClientOutAdapter;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import de.muenchen.oss.digiwf.openai.integration.application.usecase.OpenAiUseCase;
import de.muenchen.oss.digiwf.openai.integration.properties.OpenAiIntegrationProperties;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;


@Configuration
@RequiredArgsConstructor
@ComponentScan(
        basePackages = "de.muenchen.oss.digiwf.openai.integration"
)
@EnableConfigurationProperties(OpenAiIntegrationProperties.class)
public class OpenAIIntegrationAutoConfiguration {

    public final OpenAiIntegrationProperties openAiIntegrationProperties;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(openAiIntegrationProperties.getApiKey())
                .baseUrl(openAiIntegrationProperties.getBaseUrl())
                .modelName(openAiIntegrationProperties.getModel())
                .maxTokens(openAiIntegrationProperties.getMaxTokens())
                .temperature(openAiIntegrationProperties.getTemperature())
                .logRequests(openAiIntegrationProperties.getLogging())
                .logResponses(openAiIntegrationProperties.getLogging())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAiClientOutPort addressClientOutPort(
    ) {
        return new OpenAiClientOutAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public IntegrationOutPort integrationOutPort(final ProcessApi processApi, final ErrorApi errorApi) {
        return new IntegrationOutAdapter(processApi, errorApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAiInPort openAiInPort(final OpenAiClientOutPort addressClientOutPort) {
        return new OpenAiUseCase(addressClientOutPort);
    }

    // streaming adapter in

    @ConditionalOnMissingBean
    @Bean
    public MessageProcessor messageProcessor(
            final OpenAiInPort addressGermanyInPort,
            final IntegrationOutPort integrationOutPort,
            final OpenAiMapper addressServiceMapper
    ) {
        return new MessageProcessor(
                addressGermanyInPort,
                integrationOutPort,
                addressServiceMapper
        );
    }

    @Bean
    public Consumer<Message<PromptDto>> chatConsumer(final MessageProcessor messageProcessor) {
        return messageProcessor.basicChat();
    }
}
