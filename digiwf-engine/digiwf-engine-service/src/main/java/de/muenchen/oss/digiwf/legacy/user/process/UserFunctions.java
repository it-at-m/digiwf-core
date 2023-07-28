/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.user.process;

import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Functions that are used for modeling processes.
 *
 * @author externer.dl.horn
 */
@Component("user")
@RequiredArgsConstructor
public class UserFunctions {

    private final UserService userService;

    /**
     * Get the firstname for the given user id.
     *
     * @param userId Id of the user
     * @return firstname
     */
    public String firstname(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getForename();
    }

    /**
     * Get the lastname for the given user id.
     *
     * @param userId Id of the user
     * @return lastname
     */
    public String lastname(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getSurname();
    }

    /**
     * Get the username for the given user id.
     *
     * @param userId Id of the user
     * @return username
     */
    public String username(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getUsername();
    }

    /**
     * Get the email for the given user id.
     *
     * @param userId Id of the user
     * @return email
     */
    public String email(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getEmail();
    }

    /**
     * Get the salutation for the given user id.
     *
     * @param userId Id of the user
     * @return salutation
     */
    public String salutation(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getLhmTitle();
    }

    /**
     * Get the phone for the given user id.
     *
     * @param userId Id of the user
     * @return phone
     */
    public String phone(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getTelephoneNumber();
    }

    /**
     * Get the group for the given user id.
     *
     * @param userId Id of the user
     * @return group
     */
    public String ou(final String userId) {
        val user = this.userService.getUser(userId);
        return user.getOu();
    }

    /**
     * Get the user for the given user id.
     *
     * @param userId Id of the user
     * @return user
     */
    public User get(final String userId) {
        return this.userService.getUser(userId);
    }

}
