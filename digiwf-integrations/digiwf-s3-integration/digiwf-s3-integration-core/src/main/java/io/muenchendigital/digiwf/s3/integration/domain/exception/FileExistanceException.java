package io.muenchendigital.digiwf.s3.integration.domain.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExistanceException extends Exception {

    public FileExistanceException(final String message, final Exception exception) {
        super(message, exception);
    }

    public FileExistanceException(final String message) {
        super(message);
    }

}

