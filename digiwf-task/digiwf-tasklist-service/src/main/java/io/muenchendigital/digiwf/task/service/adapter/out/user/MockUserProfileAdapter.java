package io.muenchendigital.digiwf.task.service.adapter.out.user;

import io.muenchendigital.digiwf.task.service.application.port.out.user.UserNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserProfilePort;
import io.muenchendigital.digiwf.task.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public class MockUserProfileAdapter implements UserProfilePort {

  private final UserProfile user = new UserProfile(
      "123456789",
      "John",
      "Doe",
      MockUserGroupResolverAdapter.GROUP1
  );

  @Override
  @NonNull
  public UserProfile findUser(@NonNull String userId) throws UserNotFoundException {
    return user;
  }
}
