package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;

import java.util.List;

public interface TransferContentPort {

    void transferContent(List<Content> content, final String filepath, final String fileContext);

}
