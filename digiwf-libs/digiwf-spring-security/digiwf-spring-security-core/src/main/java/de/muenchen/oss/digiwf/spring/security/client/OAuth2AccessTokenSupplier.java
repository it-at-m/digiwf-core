package de.muenchen.oss.digiwf.spring.security.client;

import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;
import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Supplier for service account access token using given registration.
 */
@Component
@ConditionalOnProperty(value = "digiwf.security.service-account", matchIfMissing = true)
@RequiredArgsConstructor
public class OAuth2AccessTokenSupplier implements Supplier<OAuth2AccessToken> {

  private final SpringSecurityProperties springSecurityProperties;

  private final OAuth2AuthorizedClientManager authorizedClientManager;
  private static final String ACCESS_ROLE = "clientrole_user";
  private AnonymousAuthenticationToken anonymousUserToken;

  @PostConstruct
  void init() {
    anonymousUserToken = new AnonymousAuthenticationToken(
        springSecurityProperties.getClientRegistrationServiceAccount(),
        springSecurityProperties.getClientRegistrationServiceAccount(),
        AuthorityUtils.createAuthorityList(SecurityConfiguration.SPRING_ROLE_PREFIX + ACCESS_ROLE)
    );
  }

  @Override
  public OAuth2AccessToken get() {
    final OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
        .withClientRegistrationId(
            springSecurityProperties.getClientRegistrationServiceAccount()
        )
        .principal(anonymousUserToken)
        .build();
    final OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
    if (authorizedClient == null) {
      throw new IllegalStateException("Client credentials authorization using client registration '" +
          springSecurityProperties.getClientRegistrationServiceAccount() + "' failed.");
    }
    return authorizedClient.getAccessToken();
  }
}
