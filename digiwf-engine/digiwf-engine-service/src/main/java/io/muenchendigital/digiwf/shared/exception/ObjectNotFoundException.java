/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.exception;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown when an object is not found.
 *
 * @author externer.dl.horn
 */
@AllArgsConstructor
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(final String message) {
        super(message);
    }

}
