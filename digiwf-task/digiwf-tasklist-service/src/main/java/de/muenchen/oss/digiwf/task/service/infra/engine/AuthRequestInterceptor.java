package de.muenchen.oss.digiwf.task.service.infra.engine;

import de.muenchen.oss.digiwf.task.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
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

  private final CurrentUserPort currentUserPort;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate
        .header("Authorization", "Bearer " + oAuth2TokenProvider.get().getTokenValue()) // service account token
        .header(HttpHeaders.HEADER_AUTHORIZED_USERNAME, currentUserPort.getCurrentUserUsername()) // username of the real user
    ;
  }
}
