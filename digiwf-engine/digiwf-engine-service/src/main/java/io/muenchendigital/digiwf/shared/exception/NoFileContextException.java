package io.muenchendigital.digiwf.shared.exception;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown if no file context could be found when interaction with files.
 *
 * @author externer.dl.horn
 */
@AllArgsConstructor
public class NoFileContextException extends RuntimeException {

    public NoFileContextException(final String message) {
        super(message);
    }

}
