package de.muenchen.oss.digiwf.email.configuration;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.impl.DigiwfEmailApiImpl;
import de.muenchen.oss.digiwf.email.properties.CustomMailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
@EnableConfigurationProperties(value = CustomMailProperties.class)
public class DigiwfEmailAutoConfiguration {

    private final CustomMailProperties customMailProperties;

    @ConditionalOnMissingBean
    @Bean
    public DigiwfEmailApi digiwfEmailApi(final JavaMailSender javaMailSender) {
        return new DigiwfEmailApiImpl(javaMailSender, this.customMailProperties.getFromAddress());
    }

}
