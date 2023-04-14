package io.muenchendigital.digiwf.task.service.infra.auth;

import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.MockUserGroupResolver;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("itest")
@Configuration
public class AuthTestConfiguration {

  @Bean
  public UserGroupResolverPort mockUserGroupResolver() {
    return new MockUserGroupResolver();
  }
}
