package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.File;

public interface CreateFilePort {

    String createFile(File file, String user);

}
