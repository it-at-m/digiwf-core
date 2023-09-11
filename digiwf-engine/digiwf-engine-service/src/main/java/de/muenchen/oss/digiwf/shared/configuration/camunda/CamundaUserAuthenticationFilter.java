package de.muenchen.oss.digiwf.shared.configuration.camunda;

import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.IdentityService;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;


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
    val userId = userAuthenticationProvider.getLoggedInUser();
    val roles = new ArrayList<>(userAuthenticationProvider.getLoggedInUserRoles());
    try {
      val user = userService.getUserOrNull(userId);
      // could be a service account, add groups from LDAP if it is a user.
      user.ifPresent(value -> roles.addAll(userService.getGroups(value.getLhmObjectId())));
      log.debug("Accessing {} [ {} ]", userId, roles);
      identityService.setAuthentication(userId, roles);
      chain.doFilter(request, response);
    } finally {
      identityService.clearAuthentication();
    }
  }
}
