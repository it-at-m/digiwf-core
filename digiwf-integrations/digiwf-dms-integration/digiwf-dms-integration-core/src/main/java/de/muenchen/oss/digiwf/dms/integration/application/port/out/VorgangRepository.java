package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;

public interface VorgangRepository {

    Vorgang createVorgang(Vorgang vorgang, String user);

}
