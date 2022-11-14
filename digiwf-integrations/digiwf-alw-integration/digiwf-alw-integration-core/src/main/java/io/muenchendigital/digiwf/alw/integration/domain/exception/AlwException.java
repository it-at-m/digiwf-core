/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.domain.exception;

/**
 * Exception occuring in ALW system call.
 */
public class AlwException extends Exception {

    public AlwException(final String message, final Exception exception) {
        super(message, exception);
    }

    public AlwException(final String message) {
        super(message);
    }

}
