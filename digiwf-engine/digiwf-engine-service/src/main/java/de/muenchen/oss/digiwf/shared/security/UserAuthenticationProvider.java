/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.shared.security;

/**
 * Provides the username for the currently logged-in user.
 *
 * @author externer.dl.horn
 */
public interface UserAuthenticationProvider {

    /**
     * Get the username of the logged-in user.
     *
     * @return username
     */
    String getLoggedInUser();

}
