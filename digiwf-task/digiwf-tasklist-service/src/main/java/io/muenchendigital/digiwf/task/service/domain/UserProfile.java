package io.muenchendigital.digiwf.task.service.domain;

import lombok.Data;

/**
 * Represents the profile of the user.
 */
@Data
public class UserProfile {
  private final String userId;
  private final String firstName;
  private final String lastName;
  private final String primaryOrgUnit;
}
