/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.application.port.out;

import java.util.Set;

/**
 * Provides the username for the currently logged-in user.
 *
 * @author externer.dl.horn
 */
public interface CurrentUserPort {

    /**
     * Get the username of the logged-in user.
     *
     * @return username
     */
    String getLoggedInUsername();


    /**
     * Get the username of the logged-in user.
     *
     * @return set of roles
     */
    Set<String> getLoggedInUserRoles();

}
