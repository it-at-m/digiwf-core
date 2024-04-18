package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;

public interface ReadMetadataOutPort {

    Metadata readMetadata(final String coo, final String user);

    Metadata readContentMetadata(final String coo, final String user);

}
