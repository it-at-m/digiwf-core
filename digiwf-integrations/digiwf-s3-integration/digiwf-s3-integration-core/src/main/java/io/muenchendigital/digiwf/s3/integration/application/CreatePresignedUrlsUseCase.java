package io.muenchendigital.digiwf.s3.integration.application;

import io.minio.http.Method;
import io.muenchendigital.digiwf.s3.integration.api.mapper.PresignedUrlMapper;
import io.muenchendigital.digiwf.s3.integration.adapter.in.streaming.CreatePresignedUrlEvent;
import io.muenchendigital.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistenceException;
import io.muenchendigital.digiwf.s3.integration.domain.model.PresignedUrl;
import io.muenchendigital.digiwf.s3.integration.domain.service.FileHandlingService;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Validated
public class CreatePresignedUrlsUseCase implements CreatePresignedUrlsInPort {

  /**
   * Key in the result map.
   */
  public static final String RESULT_PRESIGNED_URLS = "presignedUrls";

  private final FileHandlingService fileHandlingService;
  private final PresignedUrlMapper presignedUrlMapper;
  private final int presignedUrlExpiresInMinutes;

  @Override
  @NonNull
  public Map<String, Object> createPresignedUrls(@Valid CreatePresignedUrlEvent event) throws S3AccessException, FileExistenceException {
    // No end of life is set for files to be saved
    final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(
        List.of(event.getPath().split(";")),
        Method.valueOf(event.getAction()),
        this.presignedUrlExpiresInMinutes // 7 days is max expiration
    );
    return Map.of(RESULT_PRESIGNED_URLS, this.presignedUrlMapper.models2Dtos(presignedUrls));
  }

}
