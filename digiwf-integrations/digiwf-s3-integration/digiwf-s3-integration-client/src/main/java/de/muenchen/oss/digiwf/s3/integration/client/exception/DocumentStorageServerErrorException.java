package de.muenchen.oss.digiwf.s3.integration.client.exception;

public class DocumentStorageServerErrorException extends Exception {

    public DocumentStorageServerErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
