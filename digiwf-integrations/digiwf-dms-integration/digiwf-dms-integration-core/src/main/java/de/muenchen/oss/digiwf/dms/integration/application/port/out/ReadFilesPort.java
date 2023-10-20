package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.File;

import java.util.List;

public interface ReadFilesPort {

    List<File> readFiles(final List<String> coos, final String user);

}
