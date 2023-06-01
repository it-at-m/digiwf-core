package io.muenchendigital.digiwf.task.service.adapter.out.auth;

import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service to resolve currently logged-in user.
 */
@Component
@RequiredArgsConstructor
public class CurrentUserSpringSecurityAdapter implements CurrentUserPort {

  public static final String USER_ID_CLAIM = "lhmObjectID";
  public static final String USERNAME_CLAIM = "user_name";

  private final UserGroupResolverPort userGroupResolver;

  @Override
  public User getCurrentUser() {
    var authentication = getCurrentAuth();
    if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      var jwt = (Jwt) authentication.getPrincipal();
      var username = Objects.requireNonNull((String) jwt.getClaims().get(USER_ID_CLAIM));
      var groups = userGroupResolver.resolveGroups(username).stream().map(String::toLowerCase).collect(Collectors.toSet());
      return new User(username, groups);
    } else {
      throw new AuthenticationCredentialsNotFoundException("Could not detect current authorized user");
    }
  }

  /**
   * Retrieves the username of the current user.
   * @return username of the current user (from the user_name claim).
   */
  @Override
  public String getCurrentUserUsername() {
    var authentication = getCurrentAuth();
    if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      var jwt = (Jwt) authentication.getPrincipal();
      return Objects.requireNonNull((String) jwt.getClaims().get(USERNAME_CLAIM));
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
