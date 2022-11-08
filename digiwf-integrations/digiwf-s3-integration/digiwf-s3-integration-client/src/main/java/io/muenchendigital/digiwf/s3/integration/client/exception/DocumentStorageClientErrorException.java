package io.muenchendigital.digiwf.s3.integration.client.exception;

public class DocumentStorageClientErrorException extends Exception {

    public DocumentStorageClientErrorException(final String message, final Exception exception) {
        super(message, exception);
    }

}
