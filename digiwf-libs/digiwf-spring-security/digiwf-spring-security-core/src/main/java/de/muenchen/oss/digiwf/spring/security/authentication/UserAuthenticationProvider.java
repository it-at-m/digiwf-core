/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.spring.security.authentication;

import org.springframework.lang.NonNull;

/**
 * Provides the username for the currently logged-in user.
 */
public interface UserAuthenticationProvider {

  /**
   * Get the username of the logged-in user.
   *
   * @return username
   */
  @NonNull
  String getLoggedInUser();
}
