package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.File;

import java.util.List;

public interface TransferFilePort {

    void transferFiles(List<File> files, final String filepath, final String fileContext);

}
