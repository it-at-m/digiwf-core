/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.external.mapper;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIObjectType;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSSearchResult;
import de.muenchen.oss.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map between {@link DMSSearchResult} and {@link LHMBAI151700GIObjectType}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface DMSSearchResultMapper extends BaseTOMapper<DMSSearchResult, LHMBAI151700GIObjectType> {

    @Override
    @Mapping(target = "objaddress", source = "LHMBAI151700Objaddress")
    @Mapping(target = "objname", source = "LHMBAI151700Objname")
    DMSSearchResult map2TO(LHMBAI151700GIObjectType model);
}
