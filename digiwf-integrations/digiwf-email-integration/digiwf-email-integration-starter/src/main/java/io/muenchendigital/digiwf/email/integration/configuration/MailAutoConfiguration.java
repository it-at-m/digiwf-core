package io.muenchendigital.digiwf.email.integration.configuration;

import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.s3.integration.client.configuration.S3IntegrationClientAutoConfiguration;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.spring.cloudstream.utils.configuration.StreamingConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@AutoConfigureAfter({S3IntegrationClientAutoConfiguration.class})
@AutoConfigureBefore({StreamingConfiguration.class})
@ComponentScan(basePackages = {"io.muenchendigital.digiwf.email.integration"})
@EnableConfigurationProperties({MailProperties.class, CustomMailProperties.class})
public class MailAutoConfiguration {

    private final MailProperties mailProperties;
    private final CustomMailProperties customMailProperties;
    private final MailConfiguration mailConfiguration;
    public static final String TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS = "sendMailFromEventBus";

    /**
     * Configures the {@link JavaMailSender}
     *
     * @return configured JavaMailSender
     */
    @Bean
    @ConditionalOnMissingBean
    public JavaMailSender getJavaMailSender() throws MessagingException {
        return mailConfiguration.getJavaMailSender(
                this.mailProperties.getHost(),
                this.mailProperties.getPort(),
                this.mailProperties.getProtocol(),
                this.mailProperties.getUsername(),
                this.mailProperties.getPassword(),
                this.mailProperties.getProperties()
        );
    }

    /**
     * Configures the {@link MailingService}
     *
     * @param javaMailSender                the configured JavaMailSender
     * @param documentStorageFileRepository a documentStorageFileRepository from the S3 library
     * @return configured MailingService
     */
    @Bean
    @ConditionalOnMissingBean
    public MailingService getMailingService(final JavaMailSender javaMailSender, final DocumentStorageFileRepository documentStorageFileRepository) {
        return new MailingService(javaMailSender, customMailProperties.getFromAddress());
    }

    /**
     * Override the custom router of the digiwf-spring-cloudstream-utils. We only have one type we need to map.
     *
     * @return the custom router
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback getEventBusRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS, TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS);
        return new RoutingCallback(typeMappings);
    }
}
