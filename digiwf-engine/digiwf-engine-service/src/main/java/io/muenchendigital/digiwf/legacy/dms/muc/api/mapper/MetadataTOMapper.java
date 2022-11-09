/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.api.mapper;

import io.muenchendigital.digiwf.legacy.dms.muc.api.transport.MetadataTO;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Metadata;
import io.muenchendigital.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link Metadata} and {@link MetadataTO}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface MetadataTOMapper extends BaseTOMapper<MetadataTO, Metadata> {

}
