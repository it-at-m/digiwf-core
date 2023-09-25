/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.spring.security.authentication;

import de.muenchen.oss.digiwf.spring.security.NoSecurityConfiguration;
import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Security provider for no-security environments.
 */
@Component
@Profile(NoSecurityConfiguration.NO_SECURITY)
@RequiredArgsConstructor
public class NoSecurityUserAuthenticationProvider implements UserAuthenticationProvider {

  public final SpringSecurityProperties springSecurityProperties;

  @Override
  @NonNull
  public String getLoggedInUser() {
    return springSecurityProperties.getFallbackUsername();
  }

  @Override
  @NonNull
  public Set<String> getLoggedInUserRoles() {
    return Arrays.stream(springSecurityProperties.getFallbackUserRoles()).collect(Collectors.toSet());
  }
}
