package de.muenchen.oss.digiwf.task.service.infra.security;

import de.muenchen.oss.digiwf.task.service.adapter.out.user.MockUserGroupResolverAdapter;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@WithSecurityContext(factory = WithKeycloakUserSecurityContextFactory.class)
@Retention(RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface WithKeycloakUser {

  String userId() default TestUser.USER_ID;

  String firstName() default TestUser.FIRSTNAME;

  String lastName() default TestUser.LASTNAME;

  String email() default TestUser.EMAIL;

  String username() default TestUser.USERNAME;

  String[] roles() default {MockUserGroupResolverAdapter.PRIMARY_USERGROUP};
}
