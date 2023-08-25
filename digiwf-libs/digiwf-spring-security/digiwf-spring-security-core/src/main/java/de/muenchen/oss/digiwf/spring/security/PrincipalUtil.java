package de.muenchen.oss.digiwf.spring.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.List;

import static de.muenchen.oss.digiwf.spring.security.SecurityConfiguration.SPRING_ROLE_PREFIX;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class PrincipalUtil {
  @SuppressWarnings("unused")
  public static List<String> extractRoles(Principal principal) {
    if (principal instanceof Authentication) {
      return extractRoles((Authentication) principal);
    }
    return emptyList();
  }

  public static List<String> extractRoles(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(role -> StringUtils.removeStart(role, SPRING_ROLE_PREFIX))
        .collect(toList());
  }
}
