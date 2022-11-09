/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.user.infrastructure;

import io.muenchendigital.digiwf.legacy.user.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository to load users.
 *
 * @author externer.dl.horn
 */
public interface UserRepository {

    /**
     * Get user by id.
     *
     * @param id Id of the user
     * @return user optional
     */
    Optional<User> findById(String id);

    /**
     * Get user by username.
     *
     * @param username Username of the user.
     * @return user optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Get users by name and groups.
     *
     * @param filter    Filter string for the names
     * @param ouFilters Groups to filter for
     * @return users
     */
    List<User> findByNamesLike(String filter, List<String> ouFilters);

    /**
     * Get users by name and groups.
     * Second and first filter do have an "or" logic.
     *
     * @param firstFilter  Filter string for the names
     * @param secondFilter Filter string for the names
     * @param ouFilters    Groups to filter for
     * @return users
     */
    List<User> findByNamesLike(String firstFilter, String secondFilter, List<String> ouFilters);

    /**
     * Get all groups of an user.
     *
     * @param id Id of the users
     * @return all groups
     */
    List<String> findOuTree(String id);

    /**
     * Get ou by shortname.
     */
    Optional<User> findOuByShortName(String shortName);
}
