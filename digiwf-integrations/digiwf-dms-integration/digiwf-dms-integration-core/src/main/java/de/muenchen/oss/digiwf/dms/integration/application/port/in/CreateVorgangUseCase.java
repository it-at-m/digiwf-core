package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;

public interface CreateVorgangUseCase {

    Procedure createVorgang(final String titel, final String fileCOO, final String user);

}
