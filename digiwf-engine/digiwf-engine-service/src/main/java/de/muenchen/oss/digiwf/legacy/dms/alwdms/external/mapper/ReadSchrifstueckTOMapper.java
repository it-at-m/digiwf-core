/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.external.mapper;

import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.external.transport.ReadSchriftstueckTO;
import de.muenchen.oss.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map between {@link AlwSchriftstueck} and {@link ReadSchriftstueckTO}
 */
@Mapper
public interface ReadSchrifstueckTOMapper extends BaseTOMapper<ReadSchriftstueckTO, AlwSchriftstueck> {

    @Override
    @Mapping(target = "name", source = "filename")
    @Mapping(target = "content", source = "filecontent")
    @Mapping(target = "extension", source = "fileextension")
    AlwSchriftstueck map(ReadSchriftstueckTO to);

    @Override
    @Mapping(target = "filename", source = "name")
    @Mapping(target = "filecontent", source = "content")
    @Mapping(target = "fileextension", source = "extension")
    ReadSchriftstueckTO map2TO(AlwSchriftstueck model);

}
