/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.alw.domain;

import de.muenchen.oss.digiwf.legacy.alw.external.PersonenInfoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service includes methods
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlwService {

    private final PersonenInfoClient personenInfoClient;

    /**
     * Get the responsible Ldap group for the given azr number.
     *
     * @param azr AZR number
     * @return responsible Ldap group
     */
    public String getResponsibleLdapGroup(final String azr) {
        try {
            return this.personenInfoClient.getResponsibleLdapGroup(azr);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
