/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.alwdms.external.mapper;

import io.muenchendigital.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import io.muenchendigital.digiwf.legacy.dms.alwdms.external.transport.ReadMetadataTO;
import io.muenchendigital.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link AlwMetadata} and {@link ReadMetadataTO}
 */
@Mapper
public interface ReadMetadataTOMapper extends BaseTOMapper<ReadMetadataTO, AlwMetadata> {

}
