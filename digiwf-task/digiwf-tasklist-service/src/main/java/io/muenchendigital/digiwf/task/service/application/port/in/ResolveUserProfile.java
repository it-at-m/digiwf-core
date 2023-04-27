package io.muenchendigital.digiwf.task.service.application.port.in;

import io.muenchendigital.digiwf.task.service.domain.UserProfile;
import org.springframework.lang.NonNull;

/**
 * Use case to retrieve user information.
 */
public interface ResolveUserProfile {

  @NonNull
  UserProfile resolveUserProfile(@NonNull String userId);
}
