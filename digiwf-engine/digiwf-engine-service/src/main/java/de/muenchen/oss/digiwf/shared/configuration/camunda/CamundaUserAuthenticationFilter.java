package de.muenchen.oss.digiwf.shared.configuration.camunda;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;

import static de.muenchen.oss.digiwf.task.HttpHeaders.HEADER_AUTHORIZED_USERNAME;


/**
 * Filter to set authentication / authorization information.
 * This information is used for restrict access to resources.
 */
@RequiredArgsConstructor
@Slf4j
class CamundaUserAuthenticationFilter implements Filter {

  private final IdentityService identityService;
  private final UserAuthenticationProvider userAuthenticationProvider;
  private final UserService userService;

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    // first try to read the header of HTTP request.
    val delegatedUserName = extractUserNameFromHeader(request);
    // take that or fallback to username from user provider, reading it from the token
    val username = delegatedUserName.orElseGet(userAuthenticationProvider::getLoggedInUser);
    try {
      val user = userService.getUserByUserName(username);
      // could be a service account
      if (user.isPresent()) {
        val groups = userService.getGroups(user.get().getLhmObjectId());
        identityService.setAuthentication(user.get().getLhmObjectId(), groups);
        log.debug("Accessing {} [ {} ]", username, groups);
      } else {
        identityService.setAuthentication(username, null);
        log.debug("Accessing {}", username);
      }
      chain.doFilter(request, response);
    } finally {
      identityService.clearAuthentication();
    }
  }

  @Override
  public void destroy() {
  }

  /**
   * Tries to detect username based on authorization header.
   *
   * @param request http request.
   * @return username or empty.
   */
  public static Optional<String> extractUserNameFromHeader(ServletRequest request) {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    return Optional.ofNullable(httpRequest.getHeader(HEADER_AUTHORIZED_USERNAME));
  }
}
