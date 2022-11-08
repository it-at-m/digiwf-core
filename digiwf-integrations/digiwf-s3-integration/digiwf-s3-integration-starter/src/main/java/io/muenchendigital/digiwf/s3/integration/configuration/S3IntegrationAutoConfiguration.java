package io.muenchendigital.digiwf.s3.integration.configuration;

import io.minio.MinioClient;
import io.muenchendigital.digiwf.s3.integration.api.mapper.PresignedUrlMapper;
import io.muenchendigital.digiwf.s3.integration.api.streaming.MessageProcessor;
import io.muenchendigital.digiwf.s3.integration.api.streaming.events.CreatePresignedUrlEvent;
import io.muenchendigital.digiwf.s3.integration.domain.service.FileHandlingService;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.S3Repository;
import io.muenchendigital.digiwf.s3.integration.properties.S3IntegrationProperties;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.Message;

import java.util.Optional;
import java.util.function.Consumer;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "io.muenchendigital.digiwf.s3.integration")
@EntityScan(basePackages = "io.muenchendigital.digiwf.s3.integration")
@ComponentScan(basePackages = "io.muenchendigital.digiwf.s3.integration")
@EnableConfigurationProperties(S3IntegrationProperties.class)
public class S3IntegrationAutoConfiguration {

    public final S3IntegrationProperties s3IntegrationProperties;

    @Bean
    public S3Repository s3Repository() throws S3AccessException {
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

    @Profile("streaming")
    @Bean
    public MessageProcessor presignedUrlEventListener(
            final CorrelateMessageService correlateMessageService,
            final FileHandlingService fileHandlingService,
            final PresignedUrlMapper presignedUrlMapper
    ) {
        return new MessageProcessor(correlateMessageService, fileHandlingService, presignedUrlMapper, this.s3IntegrationProperties.getPresignedUrlExpiresInMinutes());
    }

    @Profile("streaming")
    @Bean
    public Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl(final MessageProcessor messageProcessor) {
        return messageProcessor.createPresignedUrl();
    }

}
