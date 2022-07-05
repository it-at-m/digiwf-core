package io.muenchendigital.digiwf.s3.integration.infrastructure.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class S3AccessException extends Exception {

    public S3AccessException(final String message) {
        super(message);
    }

    public S3AccessException(final String message, final Exception exception) {
        super(message, exception);
    }

}

