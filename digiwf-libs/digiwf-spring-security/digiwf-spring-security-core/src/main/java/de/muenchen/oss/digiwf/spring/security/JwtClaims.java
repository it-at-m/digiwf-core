package de.muenchen.oss.digiwf.spring.security;

/**
 * Collection of useful claims.
 */
public enum JwtClaims {
  ;
  public static final String USER_ID = "lhmObjectID";
  public static final String GIVEN_NAME = "given_name";
  public static final String FAMILY_NAME = "family_name";
  public static final String EMAIL = "email";

  public static final String AUTHORITIES = "authorities";
  public static final String ROLES = "user_roles";

}

