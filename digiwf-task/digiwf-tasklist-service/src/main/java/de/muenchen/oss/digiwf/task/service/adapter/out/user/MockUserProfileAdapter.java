package de.muenchen.oss.digiwf.task.service.adapter.out.user;

import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserNotFoundException;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserProfilePort;
import de.muenchen.oss.digiwf.task.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class MockUserProfileAdapter implements UserProfilePort {

  private final UserProfile user = new UserProfile(
      "123456789",
      "John",
      "Doe",
      MockUserGroupResolverAdapter.PRIMARY_USERGROUP
  );

  @Override
  @NonNull
  public UserProfile findUser(@NonNull final String userId) throws UserNotFoundException {
    return this.user;
  }
}
