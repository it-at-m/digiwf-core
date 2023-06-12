package io.muenchendigital.digiwf.s3.integration.domain.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExistenceException extends Exception {

    public FileExistenceException(final String message, final Exception exception) {
        super(message, exception);
    }

    public FileExistenceException(final String message) {
        super(message);
    }

}

