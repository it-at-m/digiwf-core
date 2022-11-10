/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.filters.domain.mapper;

import io.muenchendigital.digiwf.filters.domain.model.Filter;
import io.muenchendigital.digiwf.filters.infrastructure.entity.FilterEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map btw. {@link Filter} and {@link FilterEntity}.
 */
@Mapper
public interface FilterMapper {

    List<Filter> map2Model(List<FilterEntity> list);

    Filter map2Model(FilterEntity list);

    FilterEntity map2Entity(Filter filter);

}
