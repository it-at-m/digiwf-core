package io.muenchendigital.digiwf.task.service.adapter.out.user;

import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import org.springframework.lang.NonNull;

import java.util.Set;


/**
 * Mock implementation of the group resolver returning the same group.
 */
public class MockUserGroupResolverAdapter implements UserGroupResolverPort {

  public static final String PRIMARY_USERGROUP = "GROUP1";
  private final Set<String> userGroups = Set.of("FOO", "BAR", PRIMARY_USERGROUP);

  @NonNull
  @Override
  public Set<String> resolveGroups(@NonNull final String userId) {
    return this.userGroups;
  }
}
