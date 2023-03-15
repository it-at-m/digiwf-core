package io.muenchendigital.digiwf.task.service.adapter.out.auth.user;

import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Service to resolve currently logged-in user.
 */
@Component
@RequiredArgsConstructor
public class CurrentUserPortImpl implements CurrentUserPort {

  private static final String USERNAME_CLAIM = "lhmObjectID";

  private final UserGroupResolverPort userGroupResolver;

  @Override
  public User getCurrentUser() {
    var authentication = getCurrentAuth();
    if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      var jwt = (Jwt) authentication.getPrincipal();
      var username = Objects.requireNonNull((String) jwt.getClaims().get(USERNAME_CLAIM));
      var groups = userGroupResolver.resolveGroups(username);
      return new User(username, groups);
    } else {
      throw new AuthenticationCredentialsNotFoundException("Could not detect current authorized user");
    }
  }


  /**
   * Gets current auth.
   *
   * @return authentication.
   */
  private Authentication getCurrentAuth() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
