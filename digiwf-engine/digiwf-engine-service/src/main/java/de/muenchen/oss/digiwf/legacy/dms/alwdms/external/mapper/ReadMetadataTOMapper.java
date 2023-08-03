/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.external.mapper;

import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.external.transport.ReadMetadataTO;
import de.muenchen.oss.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link AlwMetadata} and {@link ReadMetadataTO}
 */
@Mapper
public interface ReadMetadataTOMapper extends BaseTOMapper<ReadMetadataTO, AlwMetadata> {

}
