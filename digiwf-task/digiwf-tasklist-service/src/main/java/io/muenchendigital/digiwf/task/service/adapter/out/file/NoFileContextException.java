package io.muenchendigital.digiwf.task.service.adapter.out.file;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown if no file context could be found when interaction with files.
 */
@AllArgsConstructor
public class NoFileContextException extends RuntimeException {

    public NoFileContextException(final String message)  {
        super(message);
    }

}
