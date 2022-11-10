/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.user.domain.service;

import io.muenchendigital.digiwf.legacy.user.domain.model.User;
import io.muenchendigital.digiwf.legacy.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to query for users.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get groups for the given user id.
     *
     * @param userId Id for the user
     * @return groups in lowercase
     */
    public List<String> getGroups(final String userId) {
        return this.userRepository.findOuTree(userId).stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * Search users by the given string and groups.
     *
     * @param searchString Search string. is split by a space
     * @param ous          Groups of the user
     * @return users
     */
    public List<User> searchUser(final String searchString, final String ous) {
        log.debug("Searching user with {}", searchString);

        if (StringUtils.isEmpty(searchString)) {
            throw new IllegalArgumentException("Expected value in request: queryString");
        }

        final List<String> oufilterList = ous == null ? Collections.emptyList() : Arrays.asList(ous.split(","));

        final String[] queryStrings = searchString.strip().split(" ");
        final List<User> userInfos = new ArrayList<>();
        if (queryStrings.length >= 2 && !StringUtils.isEmpty(queryStrings[0]) && !StringUtils.isEmpty(queryStrings[1])) {
            final List<User> userInfosByName = this.userRepository.findByNamesLike(queryStrings[0].strip(), queryStrings[1].strip(), oufilterList);
            userInfos.addAll(userInfosByName);
        } else {
            final List<User> userInfosByName = this.userRepository.findByNamesLike(searchString.strip(), oufilterList);
            userInfos.addAll(userInfosByName);
        }

        log.debug("Hits for {}: {}", searchString, userInfos.size());
        return userInfos;
    }

    /**
     * Format a specific user.
     *
     * @param userId Id of the user
     * @return the formatted user string
     */
    public String getUserString(final String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        final Optional<User> user = this.getUserOrNull(userId);
        return user.map(value -> value.getForename() +
                " " +
                value.getSurname() +
                " (" +
                value.getOu() +
                ")").orElse(userId);
    }

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user
     */
    public User getUser(final String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with the id %s does not exist.", userId)));
    }

    /**
     * Get user by id.
     *
     * @param userId Id of the user
     * @return user optional
     */
    public Optional<User> getUserOrNull(final String userId) {
        return this.userRepository.findById(userId);
    }

    /**
     * Get user by username.
     *
     * @param username Username of the user
     * @return user optional
     */
    public Optional<User> getUserByUserName(final String username) {
        return this.userRepository.findByUsername(username);
    }

    /**
     * Get OU by shortname.
     */
    public Optional<User> getOuByShortName(final String shortname) {
        return this.userRepository.findOuByShortName(shortname);
    }

}
