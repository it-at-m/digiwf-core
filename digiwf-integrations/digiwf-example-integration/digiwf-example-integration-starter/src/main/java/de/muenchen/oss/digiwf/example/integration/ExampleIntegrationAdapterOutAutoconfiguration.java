package de.muenchen.oss.digiwf.example.integration;

import de.muenchen.oss.digiwf.example.integration.core.adapter.out.streaming.ProcessAdapter;
import de.muenchen.oss.digiwf.example.integration.core.application.port.out.ProcessResponseOutPort;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleIntegrationAdapterOutAutoconfiguration {
    @ConditionalOnMissingBean
    @Bean
    public ProcessResponseOutPort processAdapter(final ProcessApi processApi, final ErrorApi errorApi) {
        return new ProcessAdapter(processApi, errorApi);
    }
}
