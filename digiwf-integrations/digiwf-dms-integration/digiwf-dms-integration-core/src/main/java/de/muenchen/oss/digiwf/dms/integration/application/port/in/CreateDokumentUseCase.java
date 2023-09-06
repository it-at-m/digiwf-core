package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Dokument;
import de.muenchen.oss.digiwf.dms.integration.domain.DokumentArt;

public interface CreateDokumentUseCase {

    Dokument createDocument(final String vorgangCOO, final String titel, final String user, DokumentArt art, final String dateien);

}
