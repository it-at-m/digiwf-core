package io.muenchendigital.digiwf.task.service.application.port.out.auth;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Resolves groups of a user.
 */
public interface UserGroupResolverPort {

  /**
   * Resolves groups.
   *
   * @param username identity of the user.
   * @return a set of groups or empty set, if no groups are found.
   */
  @Nonnull
  Set<String> resolveGroups(String username);
}
