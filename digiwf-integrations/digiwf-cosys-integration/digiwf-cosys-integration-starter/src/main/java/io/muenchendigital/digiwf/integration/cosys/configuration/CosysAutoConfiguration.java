package io.muenchendigital.digiwf.integration.cosys.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.muenchendigital.digiwf.s3.integration.client.configuration.S3IntegrationClientAutoConfiguration;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.spring.cloudstream.utils.configuration.StreamingConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@AutoConfigureAfter({S3IntegrationClientAutoConfiguration.class})
@AutoConfigureBefore({StreamingConfiguration.class})
@ComponentScan(basePackages = {"io.muenchendigital.digiwf.integration.cosys"})
@EnableConfigurationProperties({CosysProperties.class})
public class CosysAutoConfiguration {

    private final CosysProperties cosysProperties;
    public static final String TYPE_HEADER_CREATE_COSYS_DOCUMENT_EVENT_BUS = "createCosysDocument";

    @Bean
    public CosysConfiguration cosysConfiguration() throws JsonProcessingException {
        final CosysConfiguration cosysConfiguration = new CosysConfiguration();
        cosysConfiguration.setSsoTokenClientId(this.cosysProperties.getSsoTokenClientId());
        cosysConfiguration.setSsoTokenClientSecret(this.cosysProperties.getSsoTokenClientSecret());
        cosysConfiguration.setSsoTokenRequestUrl(this.cosysProperties.getSsoTokenRequestUrl());
        cosysConfiguration.setUrl(this.cosysProperties.getUrl());

        final Map<String, String> mergeOptions = new HashMap<>();
        mergeOptions.put("--datafile", this.cosysProperties.getMerge().getDatafile());
        mergeOptions.put("--input-language", this.cosysProperties.getMerge().getInputLanguage());
        mergeOptions.put("--output-language", this.cosysProperties.getMerge().getOutputLanguage());
        mergeOptions.put("-@1", "");
        mergeOptions.put("--keep-fields", this.cosysProperties.getMerge().getKeepFields());

        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(mergeOptions);

        cosysConfiguration.setMergeOptions(json.getBytes());

        return cosysConfiguration;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback getEventBusRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(TYPE_HEADER_CREATE_COSYS_DOCUMENT_EVENT_BUS, TYPE_HEADER_CREATE_COSYS_DOCUMENT_EVENT_BUS);
        return new RoutingCallback(typeMappings);
    }


}
