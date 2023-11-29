package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;

import java.util.List;

public interface ReadContentPort {

    List<Content> readContent(final List<String> coos, final String user);

}
