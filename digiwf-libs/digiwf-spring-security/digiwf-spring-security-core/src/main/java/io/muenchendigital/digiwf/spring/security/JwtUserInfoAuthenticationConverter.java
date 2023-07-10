package io.muenchendigital.digiwf.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Stateful configuration of a Jwt Converter loading details from UserInfo endpoint.
 */
@RequiredArgsConstructor
public class JwtUserInfoAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final UserInfoAuthoritiesService userInfoService;

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt source) {
    return new JwtAuthenticationToken(source, this.userInfoService.loadAuthorities(source));
  }

}

