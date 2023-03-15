package io.muenchendigital.digiwf.task.service.infra.engine;

import io.muenchendigital.digiwf.task.service.infra.security.GrantedAuthoritiesConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

/**
 * Supplier for service account access token using given registration.
 */
@Component
@RequiredArgsConstructor
public class OAuth2AccessTokenSupplier implements Supplier<OAuth2AccessToken> {

  private final OAuth2AuthorizedClientManager authorizedClientManager;
  private static final String clientRegistrationId = "engine-service-account"; // FIXME -> load from properties.
  private AnonymousAuthenticationToken anonymousUserToken;

  @PostConstruct
  void init() {
    anonymousUserToken = new AnonymousAuthenticationToken(
        clientRegistrationId,
        clientRegistrationId,
        AuthorityUtils.createAuthorityList(GrantedAuthoritiesConverter.SPRING_ROLE_PREFIX + "USER")
    );
  }

  @Override
  public OAuth2AccessToken get() {
    final OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
        .withClientRegistrationId(clientRegistrationId)
        .principal(anonymousUserToken)
        .build();
    final OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
    if (authorizedClient == null) {
      throw new IllegalStateException("Client credentials authorization for client registration " + clientRegistrationId + " failed.");
    }
    return authorizedClient.getAccessToken();
  }
}
