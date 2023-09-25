/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.spring.security.authentication;

import de.muenchen.oss.digiwf.spring.security.PrincipalUtil;
import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;
import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static io.muenchendigital.digiwf.spring.security.client.ClientParameters.fromEnvironment;

/**
 * User authentication provider.
 * Extracts the username from the token.
 */
@Component
@Profile(SecurityConfiguration.SECURITY)
@Slf4j
public class UserAuthenticationProviderImpl implements UserAuthenticationProvider {

  private final String userNameAttribute;
  public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

  public UserAuthenticationProviderImpl(SpringSecurityProperties springSecurityProperties, Environment environment) {
    this.userNameAttribute = fromEnvironment(environment, springSecurityProperties.getClientRegistration())
        .getUserNameAttribute();
  }

  @Override
  @NonNull
  public String getLoggedInUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AbstractAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      final Jwt jwt = (Jwt) authentication.getPrincipal();
      return (String) jwt.getClaims().get(userNameAttribute);
    }
    return NAME_UNAUTHENTICATED_USER;
  }

  @Override
  @NonNull
  public Set<String> getLoggedInUserRoles() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return new HashSet<>(PrincipalUtil.extractRoles(authentication));
  }
}
