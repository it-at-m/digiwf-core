package de.muenchen.oss.digiwf.process.api;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigApiImpl;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class ProcessApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ProcessConfigApi processConfigApi(final ProcessConfigClient processConfigClient) {
        return new ProcessConfigApiImpl(processConfigClient);
    }

}
