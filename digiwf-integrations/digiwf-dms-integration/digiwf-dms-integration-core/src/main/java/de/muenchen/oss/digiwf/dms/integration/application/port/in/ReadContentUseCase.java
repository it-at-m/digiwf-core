package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import java.util.List;

public interface ReadContentUseCase {

    void readContent(List<String> fileCoos, String user, String filePath, String fileContext);

}
