package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.dms.integration.domain.VorgangArt;

public interface CreateVorgangUseCase {

    Vorgang createVorgang(final String titel, final String sachakteCOO, VorgangArt art, final String user);

}
