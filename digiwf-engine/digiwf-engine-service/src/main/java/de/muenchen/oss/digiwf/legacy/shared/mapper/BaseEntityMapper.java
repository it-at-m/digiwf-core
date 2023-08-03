/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.shared.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map between domain and entity layer.
 *
 * @param <D> Domain object
 * @param <E> Entity object
 */
public interface BaseEntityMapper<D, E> {

    E map2Entity(D model);

    D map(E entity);

    default List<D> map(final List<E> entities) {
        return entities.stream().map(this::map).collect(Collectors.toList());
    }

    default List<E> map2Entity(final List<D> models) {
        return models.stream().map(this::map2Entity).collect(Collectors.toList());
    }

}
