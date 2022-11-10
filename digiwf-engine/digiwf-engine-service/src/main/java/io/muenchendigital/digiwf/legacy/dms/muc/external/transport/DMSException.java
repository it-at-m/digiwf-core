/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.external.transport;

/**
 * Exception can be thrown when calling the DMS
 *
 * @author martin.dietrich
 */
public class DMSException extends Exception {

    private DMSStatusCode statusCode;

    public DMSException(final String message) {
        super(message);
    }

    public DMSException(final DMSStatusCode code, final String error) {
        super(String.format("%s (%d): %s", code.toString(), code.getStatuscode(), error));
        this.statusCode = code;
    }

    public DMSStatusCode getStatusCode() {
        return this.statusCode;
    }
}
