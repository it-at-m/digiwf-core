package de.muenchen.oss.digiwf.s3.integration.configuration;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper.PresignedUrlMapper;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.integration.IntegrationOutAdapter;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import de.muenchen.oss.digiwf.s3.integration.application.CreatePresignedUrlsUseCase;
import de.muenchen.oss.digiwf.s3.integration.application.FileOperationsUseCase;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import de.muenchen.oss.digiwf.s3.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.s3.integration.properties.S3IntegrationProperties;
import io.minio.MinioClient;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "de.muenchen.oss.digiwf.s3.integration")
@EntityScan(basePackages = "de.muenchen.oss.digiwf.s3.integration")
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.s3.integration")
@EnableConfigurationProperties(S3IntegrationProperties.class)
public class S3IntegrationAutoConfiguration {

  public final S3IntegrationProperties s3IntegrationProperties;

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

  @Bean
  public MessageProcessor presignedUrlEventListener(
      CreatePresignedUrlsInPort createPresignedUrlsInPort,
      IntegrationOutPort integrationOutPort,
      PresignedUrlMapper presignedUrlsMapper
  ) {
    return new MessageProcessor(
        createPresignedUrlsInPort,
        integrationOutPort,
        presignedUrlsMapper
    );
  }

  @Bean
  public CreatePresignedUrlsInPort createPresignedUrlsInPort(FileOperationsUseCase fileHandlingService) {
    return new CreatePresignedUrlsUseCase(
        fileHandlingService,
        this.s3IntegrationProperties.getPresignedUrlExpiresInMinutes()
    );
  }

  @Bean
  public IntegrationOutPort integration(ProcessApi processApi, ErrorApi errorApi) {
    return new IntegrationOutAdapter(processApi, errorApi);
  }
}
