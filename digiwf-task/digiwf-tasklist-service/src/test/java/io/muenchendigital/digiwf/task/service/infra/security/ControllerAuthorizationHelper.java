package io.muenchendigital.digiwf.task.service.infra.security;

import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static io.muenchendigital.digiwf.task.service.adapter.out.auth.CurrentUserSpringSecurityAdapter.USERNAME_CLAIM;
import static io.muenchendigital.digiwf.task.service.adapter.out.auth.CurrentUserSpringSecurityAdapter.USER_ID_CLAIM;

public class ControllerAuthorizationHelper {

  public static SecurityContext mockUser(TestUser user, String... roleNames) {
    val context = SecurityContextHolder.createEmptyContext();

    final List<String> roles = new ArrayList<>(Arrays.asList(roleNames));
    final Set<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

    val claims = new HashMap<String, Object>();
    claims.put("given_name", user.getFirstName());
    claims.put("family_name", user.getLastName());
    claims.put("email", user.getEmail());
    claims.put(USER_ID_CLAIM, user.getUserId());
    claims.put(USERNAME_CLAIM, user.getUserName());
    // user id
    claims.put(JwtClaimNames.SUB, user.getUserId());

    val token = createJwt(claims);

    context.setAuthentication(new JwtAuthenticationToken(token, authorities));
    return context;
  }

  public static OAuth2AccessToken createServiceAccessToken() {

    val claims = new HashMap<String, Object>();
    claims.put(JwtClaimNames.SUB, "some");

    return new OAuth2AccessToken(
        OAuth2AccessToken.TokenType.BEARER,
        createJwt(claims).getTokenValue(),
        Instant.now(),
        Instant.now().plus(60, ChronoUnit.MINUTES)
    );
  }

  public static Jwt createJwt(final Map<String, Object> claims) {
    final Map<String, Object> headers = new HashMap<>();
    headers.put("alg", "RS256");
    headers.put("typ", "JWT");

    val token = new Jwt(
        "test-" + UUID.randomUUID(),
        Instant.now(),
        Instant.now().plus(60, ChronoUnit.MINUTES),
        headers,
        claims
    );
    return token;
  }

}
