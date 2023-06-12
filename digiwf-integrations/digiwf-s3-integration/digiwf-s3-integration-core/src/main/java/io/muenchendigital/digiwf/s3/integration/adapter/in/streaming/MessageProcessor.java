package io.muenchendigital.digiwf.s3.integration.adapter.in.streaming;

import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;
import io.muenchendigital.digiwf.s3.integration.application.port.in.CreatePresignedUrlsInPort;
import io.muenchendigital.digiwf.s3.integration.application.port.out.IntegrationOutPort;
import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistenceException;
import io.muenchendigital.digiwf.s3.integration.adapter.out.s3.S3AccessException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import javax.validation.ConstraintViolationException;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Profile("streaming")
public class MessageProcessor {

  private final CreatePresignedUrlsInPort createPresignedUrlsInPort;
  private final IntegrationOutPort integration;

  private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
  private static final String FILE_DOES_NOT_EXIST_ERROR_CODE = "FILE_DOES_NOT_EXIST_ERROR";

  /**
   * Create pre-signed urls for the given path in {@link CreatePresignedUrlEvent}.
   * Pre-signed urls are created for all files inside a directory if the path is a directory.
   * The result is streamed to the digiwf-engine with a correlateMessage event.
   */
  @Bean
  public Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl() {
    return message -> {

      val headers = message.getHeaders();

      try {
        createPresignedUrlsInPort.createPresignedUrls(message.getPayload());
      } catch (ConstraintViolationException cve) {
        integration.handleBpmnError(headers, new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
      } catch (FileExistenceException fee) {
        integration.handleBpmnError(headers, new BpmnError(FILE_DOES_NOT_EXIST_ERROR_CODE, fee.getMessage()));
      } catch (S3AccessException sae) {
        integration.handleIncident(headers, new IncidentError(sae.getMessage()));
      }
    };
  }
}
