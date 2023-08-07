package de.muenchen.oss.digiwf.task.service.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class SingleTokenTestSupplier implements Supplier<OAuth2AccessToken> {

  private final OAuth2AccessToken token;

  @Override
  public OAuth2AccessToken get() {
    return token;
  }
}
