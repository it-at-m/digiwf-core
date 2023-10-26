package de.muenchen.oss.digiwf.task.service.infra.engine;

import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Feign request interceptor setting authorization token.
 */
@RequiredArgsConstructor
@Component
public class AuthRequestInterceptor implements RequestInterceptor {

  private final CurrentUserPort currentUserPort;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate
        .header("Authorization", "Bearer " + currentUserPort.getCurrentUserToken()) // user token
    ;
  }
}
