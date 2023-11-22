package de.muenchen.oss.digiwf.email.integration.configuration;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.email.integration.adapter.out.MailAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.ProcessAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.S3Adapter;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessagePort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailPort;
import de.muenchen.oss.digiwf.email.integration.application.usecase.SendMailUseCase;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({MailProperties.class, MetricsProperties.class})
public class MailAutoConfiguration {

    private final MetricsProperties metricsProperties;

    /**
     * Configures the {@link SendMail} use case.
     *
     * @param loadAttachmentPort   LoadMailAttachmentPort
     * @param correlateMessagePort CorrelateMessagePort
     * @param mailPort             MailPort
     * @return configured SendMail use case
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMail getSendMailUseCase(final LoadMailAttachmentPort loadAttachmentPort, final CorrelateMessagePort correlateMessagePort, final MailPort mailPort) {
        return new SendMailUseCase(loadAttachmentPort, correlateMessagePort, mailPort);
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

    @Bean
    @ConditionalOnMissingBean
    public MailPort getMailPort(final DigiwfEmailApi digiwfEmailApi) {
        return new MailAdapter(digiwfEmailApi);
    }

    // Function call had to be renamed for message routing
    @ConditionalOnMissingBean
    @Bean
    public Consumer<Message<Mail>> sendMailFromEventBus(final ErrorApi errorApi, final SendMail mailUseCase, final MonitoringService monitoringService) {
        final MessageProcessor messageProcessor = new MessageProcessor(errorApi, mailUseCase, monitoringService);
        return messageProcessor.emailIntegration();
    }

}
