package de.muenchen.oss.digiwf.s3.integration.configuration;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.PresignedUrlMapper;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming.CreatePresignedUrlEvent;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming.FilesDTO;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming.StreamingAdapter;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FolderOperationsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.usecase.CreatePresignedUrlsUseCase;
import de.muenchen.oss.digiwf.s3.integration.application.usecase.FileOperationsPresignedUrlUseCase;
import de.muenchen.oss.digiwf.s3.integration.application.usecase.FileOperationsUseCase;
import de.muenchen.oss.digiwf.s3.integration.properties.S3IntegrationProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@EntityScan(basePackages = "de.muenchen.oss.digiwf.s3.integration")
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.s3.integration")
@EnableConfigurationProperties(S3IntegrationProperties.class)
public class S3IntegrationAutoConfiguration {

    public final S3IntegrationProperties s3IntegrationProperties;

    @ConditionalOnMissingBean
    @Bean
    public S3Repository s3Repository() throws FileSystemAccessException {
        final MinioClient minioClient = MinioClient.builder()
                .endpoint(this.s3IntegrationProperties.getUrl())
                .credentials(this.s3IntegrationProperties.getAccessKey(), this.s3IntegrationProperties.getSecretKey())
                .build();
        return new S3Repository(
                this.s3IntegrationProperties.getBucketName(),
                this.s3IntegrationProperties.getUrl(),
                minioClient,
                BooleanUtils.isNotFalse(this.s3IntegrationProperties.getInitialConnectionTest()),
                this.s3IntegrationProperties.getProxyEnabled() ? Optional.of(this.s3IntegrationProperties.getProxyUrl()) : Optional.empty()
        );
    }

    @ConditionalOnMissingBean
    @Bean
    public StreamingAdapter presignedUrlEventListener(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final CreatePresignedUrlsInPort createPresignedUrlsInPort,
            final FolderOperationsInPort folderOperationsInPort,
            final FileOperationsInPort fileOperationsInPort,
            final PresignedUrlMapper presignedUrlsMapper
    ) {
        return new StreamingAdapter(
                processApi,
                errorApi,
                createPresignedUrlsInPort,
                folderOperationsInPort,
                fileOperationsInPort,
                presignedUrlsMapper
        );
    }

    @ConditionalOnMissingBean
    @Bean
    public CreatePresignedUrlsInPort createPresignedUrlsInPort(FileOperationsPresignedUrlUseCase fileHandlingService) {
        return new CreatePresignedUrlsUseCase(
                fileHandlingService,
                this.s3IntegrationProperties.getPresignedUrlExpiresInMinutes()
        );
    }

    @ConditionalOnMissingBean
    @Bean
    public FileOperationsInPort fileOperationsInPort(S3Repository s3Repository) {
        return new FileOperationsUseCase(s3Repository);
    }

    @Bean
    public Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.createPresignedUrl();
    }

    @Bean
    public Consumer<Message<FilesDTO>> deleteFiles(final StreamingAdapter streamingAdapter) {
        return streamingAdapter.deleteFiles();
    }
}
