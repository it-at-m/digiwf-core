package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import org.springframework.lang.NonNull;

import java.util.List;

public interface ListContentOutPort {
    /**
     * List all content coos for a document coo.
     *
     * @param documentCoo The document coo to list the content for.
     * @return The list of content coos contained in the document.
     */
    List<String> listContentCoos(@NonNull final String documentCoo, @NonNull final String user);
}
