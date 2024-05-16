package de.muenchen.oss.digiwf.shared.file;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.process.ProcessConfigFunctions;
import de.muenchen.oss.digiwf.s3.integration.client.ApiClient;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3DomainProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for S3 integration.
 *
 * @author martin.dietrich
 */
@Configuration
@ComponentScan(basePackages = { "de.muenchen.oss.digiwf.s3.integration.client" })
public class S3Configuration {
    /**
     * Provides the {@link RestTemplate} which is used in {@link ApiClient}.
     *
     * @return the {@link RestTemplate}.
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        /*
         * Add {@link HttpComponentsClientHttpRequestFactory} to rest template to allow
         * {@link org.springframework.http.HttpMethod.PATCH} requests.
         */
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public S3DomainProvider s3DomainProvider(final ProcessConfigFunctions processConfigFunctions) {
        return processDefinition -> processConfigFunctions.get(ProcessConfig.APP_FILE_S3_SYNC_CONFIG, processDefinition);
    }
}
