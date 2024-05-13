package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;

import java.util.List;

public interface LoadFileOutPort {

    List<Content> loadFiles(List<String> filepaths, String fileContext, String processDefinition);

}
