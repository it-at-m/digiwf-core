package de.muenchen.oss.digiwf.dms.integration.adapter.in.rest.impl;

import de.muenchen.oss.digiwf.dms.integration.adapter.in.rest.mapper.MetadataMapper;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.ReadMetadataInPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.rest.api.MetadataApiDelegate;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.rest.model.MetadataTO;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Metadata API delegate for getting metadata of an object.
 */
@Component
@RequiredArgsConstructor
public class MetadataApiDelegateImpl implements MetadataApiDelegate {

    private final MetadataMapper metadataMapper;
    private final ReadMetadataInPort readMetadataInPort;

    @Override
    public ResponseEntity<MetadataTO> readMetadata(String objectclass, String coo) {
        return ok(metadataMapper.to(readMetadataInPort.readMetadata(ObjectType.valueOf(objectclass), coo)));
    }

}
