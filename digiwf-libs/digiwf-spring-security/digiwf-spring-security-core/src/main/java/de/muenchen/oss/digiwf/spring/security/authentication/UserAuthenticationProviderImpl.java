/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.spring.security.authentication;

import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;
import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User authentication provider.
 * Extracts the username from the token.
 */
@Component
@Profile(SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationProviderImpl implements UserAuthenticationProvider {

  private final SpringSecurityProperties springSecurityProperties;
  private final ClientRegistrationRepository clientRegistrationRepository;
  private String userNameAttribute;
  public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

  @PostConstruct
  public void getUsernameAttributeName() {
    try {
      userNameAttribute = clientRegistrationRepository.findByRegistrationId(springSecurityProperties.getClientRegistration())
          .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    } catch (Exception e) {
      userNameAttribute = "user_name";
      log.error("Error reading username attribute for configured client registration "
          + springSecurityProperties.getClientRegistration() + ". Falling back to " + userNameAttribute, e);
    }
  }

  @Override
  @NonNull
  public String getLoggedInUser() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() instanceof Jwt) {
      final Jwt jwt = (Jwt) authentication.getPrincipal();
      return (String) jwt.getClaims().get(userNameAttribute);
    }
    return NAME_UNAUTHENTICATED_USER;
  }

}
