package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;

public interface CreateVorgangUseCase {

    Vorgang createVorgang(final String titel, final String sachakteCOO, final String user);

}
