package de.muenchen.oss.digiwf.task.service.infra.security;

import lombok.val;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithKeycloakUserSecurityContextFactory implements WithSecurityContextFactory<WithKeycloakUser> {
  @Override
  public SecurityContext createSecurityContext(WithKeycloakUser annotation) {
    val testUser = new TestUser(annotation.userId(), annotation.firstName(), annotation.lastName(), annotation.username(), annotation.email());
    return ControllerAuthorizationHelper.mockUser(testUser, annotation.roles());
  }
}
