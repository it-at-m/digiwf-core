package io.muenchendigital.digiwf.spring.security.client;

import lombok.Data;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OAuth2 Client parameters read directly from the environment.
 */
@Data
public class ClientParameters {
  private final String providerIssuerUrl;
  private final String providerUserInfoUri;
  private final String userNameAttribute;
  private final String clientId;
  private final List<String> scopes;

  private static final String CLIENT_ID_TMPL = "spring.security.oauth2.client.registration.%s.client-id";
  private static final String SCOPE_TMPL = "spring.security.oauth2.client.registration.%s.scope";
  private static final String PROVIDER_ISSUER_URL_TMPL = "spring.security.oauth2.client.provider.%s.issuer-uri";
  private static final String PROVIDER_USER_INFO_URL_TMPL = "spring.security.oauth2.client.provider.%s.user-info-uri";
  private static final String USER_NAME_ATTRIBUTE_TMPL = "spring.security.oauth2.client.provider.%s.user-name-attribute";

  /**
   * Creates client parameters from configuration of the Spring environment.
   *
   * @param environment    environment.
   * @param registrationId registration id using client.
   * @return client parameters.
   */
  public static ClientParameters fromEnvironment(Environment environment, String registrationId) {
    final String providerIssuerUri = environment.getProperty(String.format(PROVIDER_ISSUER_URL_TMPL, registrationId));
    final String providerUserInfoUri = environment.getProperty(String.format(PROVIDER_USER_INFO_URL_TMPL, registrationId));
    final String usernameAttribute = environment.getProperty(String.format(USER_NAME_ATTRIBUTE_TMPL, registrationId));
    final String clientId = environment.getProperty(String.format(CLIENT_ID_TMPL, registrationId));
    final String scopeString = environment.getProperty(String.format(SCOPE_TMPL, registrationId));
    return new ClientParameters(providerIssuerUri, providerUserInfoUri, usernameAttribute, clientId, splitScopes(scopeString));
  }

  /**
   * Extracts scopes from single scope string, split by ",". Removes leading and trailing spaces.
   * @param scopeString scope string from environment.
   * @return list of scopes.
   */
  static List<String> splitScopes(String scopeString) {
    if (scopeString == null || scopeString.isEmpty()) {
      return new ArrayList<>();
    }
    return Arrays.stream(scopeString.split(",")).map(String::trim).collect(Collectors.toList());
  }


}
