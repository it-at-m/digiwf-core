package io.muenchendigital.digiwf.task.service.domain;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown when an object is not found.
 */
@AllArgsConstructor
public class IllegalResourceAccessException extends RuntimeException {

    public IllegalResourceAccessException(final String message) {
        super(message);
    }

}
