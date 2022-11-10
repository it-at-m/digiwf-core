/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.filters.api.mapper;

import io.muenchendigital.digiwf.filters.api.transport.FilterTO;
import io.muenchendigital.digiwf.filters.api.transport.SaveFilterTO;
import io.muenchendigital.digiwf.filters.domain.model.Filter;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map {@link Filter} domain object into {@link FilterTO}, {@link SaveFilterTO} transport object.
 */
@Mapper
public interface FilterApiMapper {

    List<FilterTO> map2TO(List<Filter> list);

    FilterTO map2TO(Filter filter);

    Filter map(FilterTO filterTO);

    Filter map(SaveFilterTO filterTO, String userId);

}
