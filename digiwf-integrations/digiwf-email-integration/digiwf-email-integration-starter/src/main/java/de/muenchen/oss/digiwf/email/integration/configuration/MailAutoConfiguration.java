package de.muenchen.oss.digiwf.email.integration.configuration;

import de.muenchen.oss.digiwf.email.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.email.integration.application.usecase.SendMailUseCase;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import io.micrometer.core.instrument.MeterRegistry;
import de.muenchen.oss.digiwf.email.integration.adapter.out.ProcessAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.S3Adapter;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.Message;

import javax.mail.MessagingException;
import java.util.Properties;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
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

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessagePort getCorrelateMessagePort(final ProcessApi processApi) {
        return new ProcessAdapter(processApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadMailAttachmentPort getLoadMailAttachmentPort(final S3FileTransferRepository s3FileTransferRepository) {
        return new S3Adapter(s3FileTransferRepository);
    }

    @ConditionalOnMissingBean
    @Bean
    public Consumer<Message<Mail>> emailMessageProcessor(final ErrorApi errorApi, final SendMail mailUseCase, final MonitoringService monitoringService) {
        final MessageProcessor messageProcessor = new MessageProcessor(errorApi, mailUseCase, monitoringService);
        return messageProcessor.emailIntegration();
    }

}
