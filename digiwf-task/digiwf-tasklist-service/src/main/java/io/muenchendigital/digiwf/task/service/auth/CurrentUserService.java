package io.muenchendigital.digiwf.task.service.auth;

import io.holunda.polyflow.view.auth.User;

/**
 * Adapter to retrieve currently logged-in user information.
 */
public interface CurrentUserService {
    /**
     * Retrieves current user.
     * @return current user.
     */
    User getCurrentUser();
}
