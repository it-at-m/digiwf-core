package io.muenchendigital.digiwf.task.service.application.usecase;

import io.muenchendigital.digiwf.task.service.application.port.in.ResolveUserProfile;
import io.muenchendigital.digiwf.task.service.domain.UserProfile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ResolveUserProfileUseCase implements ResolveUserProfile {
  @Override
  public UserProfile resolveUserProfile(@NonNull String userId) {
    return null;
  }
}
