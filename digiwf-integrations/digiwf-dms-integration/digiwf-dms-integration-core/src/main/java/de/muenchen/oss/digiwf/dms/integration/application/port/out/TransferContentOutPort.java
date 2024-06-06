package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;

import java.util.List;

public interface TransferContentOutPort {

    void transferContent(List<Content> content, String filepath, String fileContext, String processDefinition);

}
