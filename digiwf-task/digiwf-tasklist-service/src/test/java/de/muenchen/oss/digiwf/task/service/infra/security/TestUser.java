package de.muenchen.oss.digiwf.task.service.infra.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TestUser {

  public static final String USER_ID = "123456789";
  public static final String FIRSTNAME = "John";
  public static final String LASTNAME = "Doe";
  public static final String USERNAME = "ex.john.doe";
  public static final String EMAIL = "ex.doe@muenchen.de";

  public static final TestUser JOHN_DOE = new TestUser(USER_ID, FIRSTNAME, LASTNAME, USERNAME, EMAIL);

  private final String userId;
  private final String firstName;
  private final String lastName;
  private final String userName;
  private final String email;

}
