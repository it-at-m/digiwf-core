/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.domain.exception;


public class TicketNotFoundException extends Exception {

    public TicketNotFoundException(final String message) {
        super(message);
    }

}
