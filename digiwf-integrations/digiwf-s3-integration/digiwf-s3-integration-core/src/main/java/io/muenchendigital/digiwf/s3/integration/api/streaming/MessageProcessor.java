package io.muenchendigital.digiwf.s3.integration.api.streaming;

import io.minio.http.Method;
import io.muenchendigital.digiwf.s3.integration.api.mapper.PresignedUrlMapper;
import io.muenchendigital.digiwf.s3.integration.api.streaming.events.CreatePresignedUrlEvent;
import io.muenchendigital.digiwf.s3.integration.domain.exception.FileExistanceException;
import io.muenchendigital.digiwf.s3.integration.domain.model.PresignedUrl;
import io.muenchendigital.digiwf.s3.integration.domain.service.FileHandlingService;
import io.muenchendigital.digiwf.s3.integration.infrastructure.exception.S3AccessException;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.validation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


/**
 * Spring cloud stream event listener to create presigned urls for given files.
 *
 * @author ext.dl.moesle
 */
@Profile("streaming")
@Slf4j
@RequiredArgsConstructor
public class MessageProcessor {
    private final CorrelateMessageService correlateMessageService;
    private final FileHandlingService fileHandlingService;
    private final PresignedUrlMapper presignedUrlMapper;
    private final int presignedUrlExpiresInMinutes;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * Create presigned urls for the given path in {@link CreatePresignedUrlEvent}.
     * Presigned urls are created for all files inside a directory if the path is a directory.
     * The result is streamed to the digiwf-engine with a correlateMessage event.
     */
    public Consumer<Message<CreatePresignedUrlEvent>> createPresignedUrl() {
        return message -> {
            final CreatePresignedUrlEvent event = message.getPayload();
            final Validator validator = this.validatorFactory.getValidator();
            final Set<ConstraintViolation<CreatePresignedUrlEvent>> violations = validator.validate(event);

            // event is invalid
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            try {
                // 7 days is max expiration
                // No end of life is set for files to be saved
                final List<PresignedUrl> presignedUrls = this.fileHandlingService.getPresignedUrls(List.of(event.getPath().split(";")), Method.valueOf(event.getAction()), this.presignedUrlExpiresInMinutes);
                this.emitResponse(message.getHeaders(), presignedUrls);
            } catch (final S3AccessException | FileExistanceException e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Function to emit a reponse using the correlateMessageService of digiwf-spring-cloudstream-utils
     *
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     * @param presignedUrls  A list of presigned urls for the file(s)
     */
    public void emitResponse(final MessageHeaders messageHeaders, final List<PresignedUrl> presignedUrls) {
        final Map<String, Object> correlateMsgPayload = Map.of("presignedUrls", this.presignedUrlMapper.models2Dtos(presignedUrls));
        this.correlateMessageService.sendCorrelateMessage(messageHeaders, correlateMsgPayload);
    }

}
