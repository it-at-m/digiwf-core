/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.shared.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map between domain and entity object.
 *
 * @param <D> Domain object
 * @param <E> Entity object
 */
public interface BaseMapper<D, E> {

    D map(E entity);

    default List<D> map(final List<E> entities) {
        return entities.stream().map(this::map).collect(Collectors.toList());
    }

}
