package de.muenchen.oss.digiwf.task.service.adapter.out.user;

import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserNotFoundException;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserProfilePort;
import de.muenchen.oss.digiwf.task.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Map;

@RequiredArgsConstructor
public class MockUserProfileAdapter implements UserProfilePort {

  private final UserProfile john = new UserProfile(
      "123456789",
      "John",
      "Doe",
      MockUserGroupResolverAdapter.PRIMARY_USERGROUP
  );
  private final UserProfile jane = new UserProfile(
      "234567890",
      "Jane",
      "Doe",
      MockUserGroupResolverAdapter.PRIMARY_USERGROUP
  );

  private final Map<String, UserProfile> users = Map.of(
          john.getUserId(), john,
          jane.getUserId(), jane
  );

  @Override
  @NonNull
  public UserProfile findUser(@NonNull final String userId) throws UserNotFoundException {
    final UserProfile user = users.get(userId);
    if(user  == null) {
      throw new UserNotFoundException(userId);
    }
    return user;
  }
}
