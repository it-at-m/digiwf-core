package io.muenchendigital.digiwf.connector.security.api;

import java.util.List;

/**
 * Access the current user context.
 */
public interface UserContext {

    /**
     * Get the user id of the authenticated user.
     *
     * @return username
     */
    String getLoggedInUserId();

    /**
     * Get the user groups of the authenticated user.
     *
     * @return user groups
     */
    List<String> getUserGroups();

}
