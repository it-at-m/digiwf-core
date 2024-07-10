package de.muenchen.oss.digiwf.email.integration.configuration;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.integration.adapter.in.streaming.MailWithLogoAndLinkPathsDto;
import de.muenchen.oss.digiwf.email.integration.adapter.in.streaming.MailWithLogoAndLinkPresignedDto;
import de.muenchen.oss.digiwf.email.integration.adapter.in.streaming.StreamingAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.mail.MailAdapter;
import de.muenchen.oss.digiwf.email.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPathsInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPresignedInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.application.port.out.MailOutPort;
import de.muenchen.oss.digiwf.email.integration.application.usecase.SendMailPathsUseCase;
import de.muenchen.oss.digiwf.email.integration.application.usecase.SendMailPresignedUseCase;
import de.muenchen.oss.digiwf.email.integration.domain.model.paths.TextMailPaths;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.TextMailPresigned;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
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
     * Configures the {@link SendMailPresignedInPort} use case.
     *
     * @param loadAttachmentPort LoadMailAttachmentPort
     * @param mailOutPort        MailPort
     * @return configured SendMail use case
     */
    @Bean
    @ConditionalOnMissingBean
    public SendMailPresignedInPort getSendMailPresignedInPort(final LoadMailAttachmentOutPort loadAttachmentPort, final MailOutPort mailOutPort) {
        return new SendMailPresignedUseCase(loadAttachmentPort, mailOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public SendMailPathsInPort getSendMailPathsInPort(final LoadMailAttachmentOutPort loadAttachmentPort, final MailOutPort mailOutPort) {
        return new SendMailPathsUseCase(loadAttachmentPort, mailOutPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public MonitoringService getMonitoringService(final MeterRegistry meterRegistry) {
        return new MonitoringService(meterRegistry, this.metricsProperties.getTotalMailCounterName(), this.metricsProperties.getFailureCounterName());
    }

    @Bean
    @ConditionalOnMissingBean
    public LoadMailAttachmentOutPort getLoadMailAttachmentPort(final S3FileTransferRepository s3FileTransferRepository,
                                                               final DocumentStorageFileRepository documentStorageFileRepository,
                                                               final DocumentStorageFolderRepository documentStorageFolderRepository,
                                                               final FileService fileService,
                                                               final S3StorageUrlProvider s3DomainService) {
        return new S3Adapter(s3FileTransferRepository, documentStorageFileRepository, documentStorageFolderRepository, fileService, s3DomainService);
    }

    @Bean
    @ConditionalOnMissingBean
    public MailOutPort getMailPort(final DigiwfEmailApi digiwfEmailApi) {
        return new MailAdapter(digiwfEmailApi);
    }

    @Bean
    public Consumer<Message<TextMailPresigned>> sendMailFromEventBus(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.emailIntegration();
    }

    @Bean
    public Consumer<Message<MailWithLogoAndLinkPresignedDto>> sendMailWithLogoAndLink(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.sendMailWithLogoAndLink();
    }

    @Bean
    public Consumer<Message<TextMailPaths>> sendTextMailV2(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.sendTextMailV2();
    }

    @Bean
    public Consumer<Message<MailWithLogoAndLinkPathsDto>> sendMailWithLogoAndLinkV2(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.sendMailWithLogoAndLinkV2();
    }

    @ConditionalOnMissingBean
    @Bean
    public StreamingAdapter streamingAdapter(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final MonitoringService monitoringService,
            final SendMailPresignedInPort sendMailPresignedInPort,
            final SendMailPathsInPort sendMailPathsInPort
    ) {
        return new StreamingAdapter(
                processApi,
                errorApi,
                sendMailPresignedInPort,
                sendMailPathsInPort,
                monitoringService);
    }

}
