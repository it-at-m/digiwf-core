package de.muenchen.oss.digiwf.alw.integration.application.port.out;


import org.springframework.lang.NonNull;

/**
 * Mapping ALW sachbearbeiter to a structure with a valid directory-ou.
 */
public interface OrgStructureMapper {

  /**
   * Returns the org unit of sachbearbeiter or null.
   * @param sachbearbeiter sachbearbeiter.
   * @return OU or null.
   */
  String map(@NonNull String sachbearbeiter);
}
