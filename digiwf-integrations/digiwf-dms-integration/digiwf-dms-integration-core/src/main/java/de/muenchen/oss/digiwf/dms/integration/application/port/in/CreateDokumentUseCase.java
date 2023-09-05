package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.DokumentArt;
import de.muenchen.oss.digiwf.dms.integration.domain.VorgangArt;

public interface CreateDokumentUseCase {

    void createDocument(final String vorgangCOO, final String titel, final String user, DokumentArt art, final String dateien);

}
