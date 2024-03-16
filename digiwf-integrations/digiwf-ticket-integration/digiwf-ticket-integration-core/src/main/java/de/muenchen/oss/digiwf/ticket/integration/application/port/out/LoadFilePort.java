package de.muenchen.oss.digiwf.ticket.integration.application.port.out;

import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;

import java.util.List;

public interface LoadFilePort {

    List<FileContent> loadFiles(final List<String> filepaths, final String processDefinition);

}
