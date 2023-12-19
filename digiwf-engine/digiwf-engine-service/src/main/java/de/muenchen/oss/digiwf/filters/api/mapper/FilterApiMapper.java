/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.api.mapper;

import de.muenchen.oss.digiwf.filters.api.transport.FilterTO;
import de.muenchen.oss.digiwf.filters.api.transport.SaveFilterTO;
import de.muenchen.oss.digiwf.filters.domain.model.Filter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Map {@link Filter} domain object into {@link FilterTO}, {@link SaveFilterTO} transport object.
 */
@Mapper
public interface FilterApiMapper {

    List<FilterTO> map2TO(List<Filter> list);

    FilterTO map2TO(Filter filter);

    @Mapping(target = "userId", ignore = true)
    Filter map(FilterTO filterTO);

    @Mapping(target = "id", ignore = true)
    Filter map(SaveFilterTO filterTO, String userId);

}
