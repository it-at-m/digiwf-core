/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VariablesNotValidException extends RuntimeException {

    public VariablesNotValidException(final String message) {
        super(message);
    }

}
