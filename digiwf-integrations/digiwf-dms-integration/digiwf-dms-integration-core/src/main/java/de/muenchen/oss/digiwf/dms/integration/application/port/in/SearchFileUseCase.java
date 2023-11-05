package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.File;

public interface SearchFileUseCase {
    File searchFile(String searchString, String user);
}
