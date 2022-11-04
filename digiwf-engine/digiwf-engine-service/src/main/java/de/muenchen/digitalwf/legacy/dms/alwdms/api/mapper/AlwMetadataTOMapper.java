/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.dms.alwdms.api.mapper;

import de.muenchen.digitalwf.legacy.dms.alwdms.api.transport.AlwMetaDataTO;
import de.muenchen.digitalwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.digitalwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link AlwMetadata} and {@link AlwMetaDataTO}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface AlwMetadataTOMapper extends BaseTOMapper<AlwMetaDataTO, AlwMetadata> {
}
