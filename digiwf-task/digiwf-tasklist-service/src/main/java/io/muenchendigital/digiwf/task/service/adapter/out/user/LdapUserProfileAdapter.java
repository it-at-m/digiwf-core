package io.muenchendigital.digiwf.task.service.adapter.out.user;

import feign.FeignException;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserProfilePort;
import io.muenchendigital.digiwf.task.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Slf4j
public class LdapUserProfileAdapter implements UserProfilePort {

  private final EasyLdapClient easyLdapClient;
  @Override
  @NonNull
  public UserProfile findUser(@NonNull String userId) throws UserNotFoundException {
    try {
      val data = easyLdapClient.getUserById(userId);
      return new UserProfile(
          data.getLhmObjectId(),
          data.getFirstName(),
          data.getLastName(),
          data.getOrganizationalUnit()
      );
    } catch (FeignException e) {
      log.trace("Error resolving user " + userId, e);
      throw new UserNotFoundException(userId);
    }
  }
}
