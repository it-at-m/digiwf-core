package io.muenchendigital.digiwf.shared.exception;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown if a request is in conflict with an existing resource.
 *
 * @author martin.dietrich
 */
@AllArgsConstructor
public class ConflictingResourceException extends RuntimeException {

    public ConflictingResourceException(final String message) {
        super(message);
    }

}
