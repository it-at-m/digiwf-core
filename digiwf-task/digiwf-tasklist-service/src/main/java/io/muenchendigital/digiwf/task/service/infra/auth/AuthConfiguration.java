package io.muenchendigital.digiwf.task.service.auth;

import io.muenchendigital.digiwf.task.service.auth.group.MockUserGroupResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configures authentication and authorization facilities.
 */
@Configuration
public class AuthConfiguration {

  /**
   * Mock resolver not using LDAP but always returning groups "group1" and "group2".
   *
   * @return mock user group resolver.
   */
  @Bean
  @Profile("no-ldap")
  public UserGroupResolver mockUserGroupResolver() {
    return new MockUserGroupResolver();
  }
}
