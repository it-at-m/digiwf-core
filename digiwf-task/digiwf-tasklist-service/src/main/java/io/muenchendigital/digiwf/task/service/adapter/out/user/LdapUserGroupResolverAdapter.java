package io.muenchendigital.digiwf.task.service.adapter.out.user;

import feign.FeignException;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.UserInfoResponse;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class LdapUserGroupResolverAdapter implements UserGroupResolverPort {

  private final EasyLdapClient easyLdapClient;

  @NonNull
  @Override
  public Set<String> resolveGroups(@NonNull String userId) {
    try {
      UserInfoResponse userInfoResponse = easyLdapClient.getUserById(userId);
      return Collections.singleton(userInfoResponse.getOrganizationalUnit());
    } catch (FeignException e) {
      return Collections.emptySet();
    }
  }
}
