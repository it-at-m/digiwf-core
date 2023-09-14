package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;

import java.util.List;

public interface LoadFilePort {

    List<Content> loadFiles(final List<String> filepaths, final String fileContext);

}
