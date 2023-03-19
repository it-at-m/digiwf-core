package io.muenchendigital.digiwf.task.service.infra.engine;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

import static io.muenchendigital.digiwf.task.HttpHeaders.HEADER_AUTHORIZED_USERNAME;

/**
 * Feign request interceptor setting authorization token.
 */
@RequiredArgsConstructor
@Component
public class AuthRequestInterceptor implements RequestInterceptor {

  private final Supplier<OAuth2AccessToken> oAuth2TokenProvider;

  private final CurrentUserPort currentUserPort;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate
        .header("Authorization", "Bearer " + oAuth2TokenProvider.get().getTokenValue()) // service account token
        .header(HEADER_AUTHORIZED_USERNAME, currentUserPort.getCurrentUserUsername()) // username of the real user
    ;
  }
}
