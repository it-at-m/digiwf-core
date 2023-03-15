package io.muenchendigital.digiwf.task.service.infra.engine;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

/**
 * Feign request interceptor setting authorization token.
 */
@RequiredArgsConstructor
@Component
public class AuthRequestInterceptor implements RequestInterceptor {

  private final Supplier<OAuth2AccessToken> oAuth2TokenProvider;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", "Bearer" + oAuth2TokenProvider.get().getTokenValue());
  }
}
