package io.muenchendigital.digiwf.task.service.infra.auth;

import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.LdapUserGroupResolver;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.MockUserGroupResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configures authentication and authorization facilities.
 */
@Configuration
@EnableFeignClients(clients = { EasyLdapClient.class })
public class AuthConfiguration {


  /**
   * Mock resolver not using LDAP but always returning groups "group1" and "group2".
   *
   * @return mock user group resolver.
   */
  @Bean
  @Profile("no-ldap")
  public UserGroupResolverPort mockUserGroupResolver() {
    return new MockUserGroupResolver();
  }

  @Bean
  @Profile("!no-ldap")
  public UserGroupResolverPort easyLdapUserGroupResolver(EasyLdapClient easyLdapClient) {
    return new LdapUserGroupResolver(easyLdapClient);
  }

}
