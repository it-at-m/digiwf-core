package io.muenchendigital.digiwf.task.service.adapter.out.auth.group;

import com.google.common.collect.Sets;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class MockUserGroupResolver implements UserGroupResolverPort {

  public static final String GROUP1 = "group1";

  @NotNull
  @Override
  public Set<String> resolveGroups(String username) {
    return Sets.newHashSet(GROUP1);
  }
}
