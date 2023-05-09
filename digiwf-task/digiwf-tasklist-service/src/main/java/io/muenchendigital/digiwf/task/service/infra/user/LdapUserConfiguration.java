package io.muenchendigital.digiwf.task.service.infra.user;

import io.muenchendigital.digiwf.task.service.adapter.out.user.LdapUserProfileAdapter;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.adapter.out.user.LdapUserGroupResolverAdapter;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import io.muenchendigital.digiwf.task.service.adapter.out.user.MockUserGroupResolverAdapter;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserProfilePort;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configures authentication and authorization facilities.
 */
@Profile("!no-ldap")
@Configuration
@EnableFeignClients(clients = { EasyLdapClient.class })
public class LdapUserConfiguration {

  @Bean
  public UserGroupResolverPort easyLdapUserGroupResolver(EasyLdapClient easyLdapClient) {
    return new LdapUserGroupResolverAdapter(easyLdapClient);
  }

  @Bean
  public UserProfilePort easyLdapUserProfileAdapter(EasyLdapClient easyLdapClient) {
    return new LdapUserProfileAdapter(easyLdapClient);
  }
}
