package de.muenchen.oss.digiwf.email.integration.configuration;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.adapter.in.MailWithLogoAndLinkDto;
import de.muenchen.oss.digiwf.email.integration.adapter.in.MessageProcessor;
import de.muenchen.oss.digiwf.email.integration.adapter.out.MailAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.ProcessAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.S3Adapter;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailOutPort;
import de.muenchen.oss.digiwf.email.integration.application.usecase.SendMailUseCase;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.email.integration.model.TextMail;
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
     * Configures the {@link SendMailInPort} use case.
     *
     * @param loadAttachmentPort   LoadMailAttachmentPort
     * @param correlateMessageOutPort CorrelateMessagePort
     * @param mailOutPort             MailPort
     * @return configured SendMail use case
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMailInPort getSendMailUseCase(final LoadMailAttachmentOutPort loadAttachmentPort, final CorrelateMessageOutPort correlateMessageOutPort, final MailOutPort mailOutPort) {
        return new SendMailUseCase(loadAttachmentPort, correlateMessageOutPort, mailOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public MonitoringService getMonitoringService(final MeterRegistry meterRegistry) {
        return new MonitoringService(meterRegistry, this.metricsProperties.getTotalMailCounterName(), this.metricsProperties.getFailureCounterName());
    }

    @Bean
    @ConditionalOnMissingBean
    public CorrelateMessageOutPort getCorrelateMessagePort(final ProcessApi processApi) {
        return new ProcessAdapter(processApi);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadMailAttachmentOutPort getLoadMailAttachmentPort(final S3FileTransferRepository s3FileTransferRepository) {
        return new S3Adapter(s3FileTransferRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public MailOutPort getMailPort(final DigiwfEmailApi digiwfEmailApi) {
        return new MailAdapter(digiwfEmailApi);
    }

    // Function call had to be renamed for message routing
    @Bean
    public Consumer<Message<TextMail>> sendMailFromEventBus(final MessageProcessor messageProcessor) {
        return messageProcessor.emailIntegration();
    }

    @Bean
    public Consumer<Message<MailWithLogoAndLinkDto>> sendMailWithLogoAndLink(final MessageProcessor messageProcessor) {
        return messageProcessor.sendMailWithLogoAndLink();
    }

    @ConditionalOnMissingBean
    @Bean
    public MessageProcessor createMessageProcessor(
            final ErrorApi errorApi,
            final MonitoringService monitoringService,
            final SendMailInPort mailUseCase
    ) {
        return new MessageProcessor(
                errorApi,
                mailUseCase,
                monitoringService);
    }

}
