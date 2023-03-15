package io.muenchendigital.digiwf.email.integration.configuration;

import io.muenchendigital.digiwf.email.integration.adapter.MailAdapter;
import io.muenchendigital.digiwf.email.integration.application.port.SendMailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;

@Configuration
@RequiredArgsConstructor
//@AutoConfigureAfter({S3IntegrationClientAutoConfiguration.class})
@ComponentScan(basePackages = {"io.muenchendigital.digiwf.email.integration"})
@EnableConfigurationProperties({MailProperties.class, CustomMailProperties.class})
public class MailAutoConfiguration {

    private final MailProperties mailProperties;
//    private final CustomMailProperties customMailProperties;
//    private final MailConfiguration mailConfiguration;

//    /**
//     * Configures the {@link JavaMailSender}
//     *
//     * @return configured JavaMailSender
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public JavaMailSender getJavaMailSender() throws MessagingException {
//        return this.mailConfiguration.getJavaMailSender(
//                this.mailProperties.getHost(),
//                this.mailProperties.getPort(),
//                this.mailProperties.getProtocol(),
//                this.mailProperties.getUsername(),
//                this.mailProperties.getPassword(),
//                this.mailProperties.getProperties()
//        );
//    }
//
//    /**
//     * Configures the {@link MailingService}
//     *
//     * @param javaMailSender                the configured JavaMailSender
////     * @param documentStorageFileRepository a documentStorageFileRepository from the S3 library
//     * @return configured MailingService
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public MailingService getMailingService(final JavaMailSender javaMailSender) {
//        return new MailingService(javaMailSender, this.customMailProperties.getFromAddress());
//    }

    @Bean
    @ConditionalOnMissingBean
    public SendMailPort getMailAdapter() throws MessagingException {
        return new MailAdapter(
                this.mailProperties.getHost(),
                this.mailProperties.getPort(),
                this.mailProperties.getProtocol(),
                this.mailProperties.getUsername(),
                this.mailProperties.getPassword(),
                this.mailProperties.getProperties()
        );
    }
}
