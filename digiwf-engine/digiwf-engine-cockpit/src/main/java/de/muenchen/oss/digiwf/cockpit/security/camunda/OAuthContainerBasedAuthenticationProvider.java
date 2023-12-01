package de.muenchen.oss.digiwf.cockpit.security.camunda;

import de.muenchen.oss.digiwf.spring.security.PrincipalUtil;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;

import static java.util.Collections.emptyList;

/**
 * Similar to camunda's {@link org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider}
 * but also adds SSO roles to the authentication result.
 * This filter is installed after the Spring Security filter which uses passed JWT token to extract the identity
 * of the user and write SSO roles into the corresponding token claim.
 */
public class OAuthContainerBasedAuthenticationProvider implements AuthenticationProvider {

  @Override
  public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {
    Principal principal = request.getUserPrincipal();

    if (principal == null) {
      return AuthenticationResult.unsuccessful();
    }

    String name = principal.getName();
    if (name == null || name.isEmpty()) {
      return AuthenticationResult.unsuccessful();
    }

    AuthenticationResult result = AuthenticationResult.successful(name);
    result.setGroups(PrincipalUtil.extractRoles(principal));
    result.setTenants(emptyList());
    return result;
  }

  @Override
  public void augmentResponseByAuthenticationChallenge(HttpServletResponse response, ProcessEngine engine) {
    // noop
  }
}
