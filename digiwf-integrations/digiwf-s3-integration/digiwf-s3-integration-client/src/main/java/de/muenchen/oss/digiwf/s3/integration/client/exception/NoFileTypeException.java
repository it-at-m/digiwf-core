package de.muenchen.oss.digiwf.s3.integration.client.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoFileTypeException extends RuntimeException {
    public NoFileTypeException(final String message) {
        super(message);
    }
}
