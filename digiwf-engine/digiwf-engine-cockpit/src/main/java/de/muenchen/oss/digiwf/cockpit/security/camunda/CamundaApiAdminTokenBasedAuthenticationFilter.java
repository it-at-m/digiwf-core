package de.muenchen.oss.digiwf.cockpit.security.camunda;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;
import org.camunda.bpm.webapp.impl.security.auth.Authentication;
import org.camunda.bpm.webapp.impl.security.auth.Authentications;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * We set up {@link OAuthContainerBasedAuthenticationProvider} to extract the authenticated user from the request.
 * If the user is authenticated, this filter will create authentication for the DEFAULT engine (in Spring Boot the only
 * engine that exists).
 * We use this filter on access to all resources which usually rely on session-based authentication of Camunda engine.
 */
@RequiredArgsConstructor
public class CamundaApiAdminTokenBasedAuthenticationFilter extends ContainerBasedAuthenticationFilter {

  private static final String DEFAULT_ENGINE = "default";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    val engine = getAddressedEngine(DEFAULT_ENGINE);

    AuthenticationResult authenticationResult = authenticationProvider.extractAuthenticatedUser(req, engine);
    if (authenticationResult.isAuthenticated()) {
      Authentications authentications = Authentications.getFromSession(req.getSession());
      String authenticatedUser = authenticationResult.getAuthenticatedUser();

      if (!existisAuthentication(authentications, DEFAULT_ENGINE, authenticatedUser)) {
        List<String> groups = authenticationResult.getGroups();
        List<String> tenants = authenticationResult.getTenants();

        Authentication authentication = createAuthentication(engine, authenticatedUser, groups, tenants);
        authentications.addAuthentication(authentication);
      }
      chain.doFilter(request, response);
    } else {
      resp.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
      authenticationProvider.augmentResponseByAuthenticationChallenge(resp, engine);
    }
  }
}
