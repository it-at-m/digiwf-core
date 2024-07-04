/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.configuration;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.s3.integration.client.properties.SupportedFileExtensions;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileExtensionService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3DomainProvider;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming.TicketStreamingAdapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming.WriteArticleDto;
import de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad.ZammadAdapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import de.muenchen.oss.digiwf.ticket.integration.application.usecase.WriteArticleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(TicketingProperties.class)
public class TicketIntegrationAutoConfiguration {

    @Bean
    public TicketOutPort ticketOutPort(final TicketsApi ticketsApi) {
        return new ZammadAdapter(ticketsApi);
    }

    @Bean
    public LoadFileOutPort loadFileOutPort(final DocumentStorageFileRepository documentStorageFileRepository,
                                           final DocumentStorageFolderRepository documentStorageFolderRepository, final FileExtensionService fileExtensionService,
                                           final S3StorageUrlProvider s3StorageUrlProvider) {
        return new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, fileExtensionService, s3StorageUrlProvider);
    }

    @Bean
    public WriteArticleInPort writeArticleUseCase(final TicketOutPort ticketOutPort, final LoadFileOutPort loadFileOutPort) {
        return new WriteArticleUseCase(ticketOutPort, loadFileOutPort);
    }

    @ConditionalOnMissingBean
    @Bean
    public TicketStreamingAdapter ticketStreamingAdapter(final WriteArticleInPort writeArticleInPort,
                                                         final ProcessApi processApi,
                                                         final ErrorApi errorApi) {
        return new TicketStreamingAdapter(writeArticleInPort, processApi, errorApi);
    }

    @Bean
    public Consumer<Message<WriteArticleDto>> writeArticle(final TicketStreamingAdapter messageProcessor) {
        return messageProcessor.writeArticle();
    }

    /**
     * Offers a {@link java.util.Map} of supported file extensions for this integration in form of a {@link SupportedFileExtensions} object.
     *
     * @param ticketingProperties {@link TicketingProperties}  contains the supported file extensions.
     * @return {@link SupportedFileExtensions} object representing the supported file extensions.
     */
    @Bean
    public SupportedFileExtensions supportedFileExtensions(final TicketingProperties ticketingProperties) {
        final SupportedFileExtensions supportedFileExtensions = new SupportedFileExtensions();
        supportedFileExtensions.putAll(ticketingProperties.getSupportedFileExtensions());
        return supportedFileExtensions;
    }

    /**
     * {@link S3DomainProvider} instance specifically tailored for this integration to retrieve the domain-specific S3 storage URL for a given process if its
     * process configuration contains a value for {@link de.muenchen.oss.digiwf.process.api.config.ProcessConfigConstants#APP_FILE_S3_SYNC_CONFIG}.
     *
     * @param processConfigApi {@link ProcessConfigApi} offers access to a process configuration for a given process definition id.
     * @return S3DomainProvider {@link S3DomainProvider} that retrieves the domain-specific S3 storage url for a process if configured.
     */
    @Bean
    public S3DomainProvider s3DomainProvider(final ProcessConfigApi processConfigApi) {
        return processConfigApi::getAppFileS3SyncConfig;
    }
}
