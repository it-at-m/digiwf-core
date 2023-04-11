package io.muenchendigital.digiwf.task.service.adapter.out.auth.group;

import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;


/**
 * Mock implementation of the group resolver returning the same group.
 */
public class MockUserGroupResolver implements UserGroupResolverPort {

  public static final String GROUP1 = "group1";

  @Nonnull
  @Override
  public Set<String> resolveGroups(String username) {
    return Collections.singleton(GROUP1);
  }
}
