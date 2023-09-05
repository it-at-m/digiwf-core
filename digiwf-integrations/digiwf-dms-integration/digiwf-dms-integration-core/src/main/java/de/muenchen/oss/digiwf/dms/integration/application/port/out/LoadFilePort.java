package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Schriftstueck;

import java.util.ArrayList;
import java.util.List;

public interface LoadFilePort {

    List<Schriftstueck> loadFiles(final String dateien);

}
