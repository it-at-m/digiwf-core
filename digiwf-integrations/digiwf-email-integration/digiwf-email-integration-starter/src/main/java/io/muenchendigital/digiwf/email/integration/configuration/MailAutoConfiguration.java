package io.muenchendigital.digiwf.email.integration.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.muenchendigital.digiwf.email.integration.application.port.in.SendMail;
import io.muenchendigital.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import io.muenchendigital.digiwf.email.integration.application.usecase.SendMailUseCase;
import io.muenchendigital.digiwf.email.integration.infrastructure.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"io.muenchendigital.digiwf.email.integration"})
@EnableConfigurationProperties({MailProperties.class, CustomMailProperties.class, MetricsProperties.class})
public class MailAutoConfiguration {

    private final MailProperties mailProperties;
    private final CustomMailProperties customMailProperties;
    private final MetricsProperties metricsProperties;

    /**
     * Configures the {@link JavaMailSender}
     *
     * @return configured JavaMailSender
     */
    @Bean
    @ConditionalOnMissingBean
    public JavaMailSender getJavaMailSender() throws MessagingException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailProperties.getHost());
        mailSender.setPort(this.mailProperties.getPort());
        mailSender.setProtocol(this.mailProperties.getProtocol());
        mailSender.setUsername(this.mailProperties.getUsername());
        mailSender.setPassword(this.mailProperties.getPassword());

        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(this.mailProperties.getProperties());
        mailSender.setJavaMailProperties(props);
        mailSender.testConnection();
        return mailSender;
    }

    /**
     * Configures the {@link SendMail} use case.
     *
     * @param javaMailSender       JavaMailSender
     * @param loadAttachmentPort   LoadMailAttachmentPort
     * @param correlateMessagePort CorrelateMessagePort
     * @return configured SendMail use case
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMail getSendMailUseCase(final JavaMailSender javaMailSender, final LoadMailAttachmentPort loadAttachmentPort, final CorrelateMessagePort correlateMessagePort) {
        return new SendMailUseCase(javaMailSender, loadAttachmentPort, correlateMessagePort, this.customMailProperties.getFromAddress());
    }

    @Bean
    @ConditionalOnMissingBean
    public MonitoringService getMonitoringService(final MeterRegistry meterRegistry) {
        return new MonitoringService(meterRegistry, this.metricsProperties.getTotalMailCounterName(), this.metricsProperties.getFailureCounterName());
    }

}
