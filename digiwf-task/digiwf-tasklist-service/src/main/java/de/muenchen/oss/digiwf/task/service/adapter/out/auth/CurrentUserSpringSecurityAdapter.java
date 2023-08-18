package de.muenchen.oss.digiwf.task.service.adapter.out.auth;

import de.muenchen.oss.digiwf.spring.security.PrincipalUtil;
import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import io.holunda.polyflow.view.auth.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

import static io.muenchendigital.digiwf.spring.security.client.ClientParameters.fromEnvironment;

/**
 * Service to resolve currently logged-in user.
 */
@Component
public class CurrentUserSpringSecurityAdapter implements CurrentUserPort {

  private final UserGroupResolverPort userGroupResolver;
  private final String userNameAttribute;

  public CurrentUserSpringSecurityAdapter(UserGroupResolverPort userGroupResolver, SpringSecurityProperties springSecurityProperties, Environment environment) {
    this.userGroupResolver = userGroupResolver;
    this.userNameAttribute = fromEnvironment(environment, springSecurityProperties.getClientRegistration())
        .getUserNameAttribute();
  }

  @Override
  public String getCurrentUserToken() {
    var authentication = getCurrentAuth();
    if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      var jwt = (Jwt) authentication.getPrincipal();
      return jwt.getTokenValue();
    } else {
      throw new AuthenticationCredentialsNotFoundException("Could not detect current authorized user");
    }
  }

  @Override
  public User getCurrentUser() {
    var authentication = getCurrentAuth();
    if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
      var jwt = (Jwt) authentication.getPrincipal();
      var username = Objects.requireNonNull((String) jwt.getClaims().get(userNameAttribute));
      var groups = userGroupResolver.resolveGroups(username).stream().map(String::toLowerCase).collect(Collectors.toSet());
      val roles = PrincipalUtil.extractRoles(authentication);
      groups.addAll(roles);
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
      return Objects.requireNonNull((String) jwt.getClaims().get(userNameAttribute));
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
