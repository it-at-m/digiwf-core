/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.alw.process;

import de.muenchen.oss.digiwf.legacy.alw.domain.AlwService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This Delegate get's called to resovle the responsible LDAP Group for the given AZR Number
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@Deprecated
public class KvrPersonenInfoDelegate extends AlwPersonenInfoDelegate {

    @Autowired
    public KvrPersonenInfoDelegate(final AlwService kvrService) {
        super(kvrService);
    }
}
