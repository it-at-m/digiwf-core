package de.muenchen.oss.digiwf.task.service.infra.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.task.PolyflowObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {
    @Bean
    @Primary
    public ObjectMapper primaryObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }

}
