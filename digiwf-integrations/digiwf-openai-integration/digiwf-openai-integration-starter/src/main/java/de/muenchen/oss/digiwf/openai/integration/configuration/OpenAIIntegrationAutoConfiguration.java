package de.muenchen.oss.digiwf.openai.integration.configuration;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.OpenAiMapper;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.StreamingAdapter;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.ai.Assistant;
import de.muenchen.oss.digiwf.openai.integration.adapter.out.ai.OpenAiClientOutAdapter;
import de.muenchen.oss.digiwf.openai.integration.application.port.in.OpenAiInPort;
import de.muenchen.oss.digiwf.openai.integration.application.port.out.OpenAiClientOutPort;
import de.muenchen.oss.digiwf.openai.integration.application.usecase.OpenAiUseCase;
import de.muenchen.oss.digiwf.openai.integration.properties.AzureIntegrationProperties;
import de.muenchen.oss.digiwf.openai.integration.properties.OpenAiIntegrationProperties;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import java.util.function.Consumer;


@Configuration
@RequiredArgsConstructor
@ComponentScan(
        basePackages = {
                "de.muenchen.oss.digiwf.openai.integration",
                "dev.langchain4j"
        }
)
@EnableConfigurationProperties
public class OpenAIIntegrationAutoConfiguration {

    public final OpenAiIntegrationProperties openAiIntegrationProperties;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    @Profile("azure")
    public ChatLanguageModel chatLanguageModelAzure(AzureIntegrationProperties azureIntegrationProperties) {
        return AzureOpenAiChatModel.builder()
                .apiKey(openAiIntegrationProperties.getApiKey())
                .serviceVersion(azureIntegrationProperties.getApiVersion())
                .endpoint(azureIntegrationProperties.getResource())
                .deploymentName(azureIntegrationProperties.getDeploymentName())
                .maxTokens(openAiIntegrationProperties.getMaxTokens())
                .temperature(openAiIntegrationProperties.getTemperature())
                .logRequestsAndResponses(openAiIntegrationProperties.getLogging())
                .build();
    }

    @Bean
    @Profile("!azure")
    public ChatLanguageModel chatLanguageModelOpenAI() {
        return OpenAiChatModel.builder()
                .apiKey(openAiIntegrationProperties.getApiKey())
                .modelName(openAiIntegrationProperties.getModel())
                .baseUrl(openAiIntegrationProperties.getBaseUrl())
                .maxTokens(openAiIntegrationProperties.getMaxTokens())
                .temperature(openAiIntegrationProperties.getTemperature())
                .logRequests(openAiIntegrationProperties.getLogging())
                .logResponses(openAiIntegrationProperties.getLogging())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAiClientOutPort addressClientOutPort(Assistant assistant) {
        return new OpenAiClientOutAdapter(assistant);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAiInPort openAiInPort(final OpenAiClientOutPort addressClientOutPort) {
        return new OpenAiUseCase(addressClientOutPort);
    }

    // streaming adapter in

    @ConditionalOnMissingBean
    @Bean
    public StreamingAdapter messageProcessor(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final OpenAiInPort addressGermanyInPort,
            final OpenAiMapper addressServiceMapper
    ) {
        return new StreamingAdapter(
                processApi,
                errorApi,
                addressGermanyInPort,
                addressServiceMapper
        );
    }

    @Bean
    public Consumer<Message<PromptDto>> basicChat(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.basicChat();
    }

    @Bean
    public Consumer<Message<TranslateDto>> translate(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.translate();
    }

    @Bean
    public Consumer<Message<SummarizeDto>> summarize(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.summarize();
    }

    @Bean
    public Consumer<Message<GenerateMailDto>> generateMail(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.generateMail();
    }

    @Bean
    public Consumer<Message<ExtractDataDto>> extractData(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.extractData();
    }

    @Bean
    public Consumer<Message<ClassifyDto>> classify(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.classify();
    }
}
