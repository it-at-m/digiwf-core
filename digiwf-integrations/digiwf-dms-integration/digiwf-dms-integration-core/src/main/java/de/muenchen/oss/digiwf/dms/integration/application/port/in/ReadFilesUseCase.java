package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import java.util.List;

public interface ReadFilesUseCase {

    void readFiles(List<String> fileCoos, String user, String filePath, String fileContext);

}
