package io.muenchendigital.digiwf.task.service.infra.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TestUser {

  public static final TestUser JOHN_DOE = new TestUser("123456789", "John", "Doe", "ex.john.doe", "ex.doe@muenchen.de");

  private final String userId;
  private final String firstName;
  private final String lastName;
  private final String userName;
  private final String email;

}
