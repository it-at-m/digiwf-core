package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.File;

import java.util.List;

public interface SearchFilePort {
    List<File> searchFile(String searchString, String user);
}
