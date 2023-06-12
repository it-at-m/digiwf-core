package io.muenchendigital.digiwf.s3.integration.application.port.in;

import io.muenchendigital.digiwf.s3.integration.adapter.in.streaming.CreatePresignedUrlEvent;
import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistenceException;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AccessException;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.Map;

public interface CreatePresignedUrlsInPort {
  /**
   * Create pre-signed URLs.
   * @param event event containing the request.
   * @return resulting variable map.
   * @throws S3AccessException on S3 errors.
   * @throws FileExistenceException on non-existing file references.
   * @throws javax.validation.ConstraintViolationException if the request is not valid.
   */
  @NonNull
  Map<String, Object> createPresignedUrls(@Valid CreatePresignedUrlEvent event) throws S3AccessException, FileExistenceException;
}
