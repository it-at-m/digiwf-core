package io.muenchendigital.digiwf.task.service.adapter.in.rest.impl;

import io.muenchendigital.digiwf.task.service.adapter.in.rest.mapper.UserProfileMapper;
import io.muenchendigital.digiwf.task.service.application.port.in.ResolveUserProfile;
import io.muenchendigital.digiwf.task.service.application.port.in.rest.api.UserApiDelegate;
import io.muenchendigital.digiwf.task.service.application.port.in.rest.model.UserProfileTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.ResponseEntity.ok;

@Component
@RequiredArgsConstructor
public class UserApiDelegateImpl implements UserApiDelegate {

  private final UserProfileMapper userMapper;
  private final ResolveUserProfile resolvePort;
  @Override
  public ResponseEntity<UserProfileTO> resolveUser(String userId) {
    return ok(userMapper.to(resolvePort.resolveUserProfile(userId)));
  }
}
