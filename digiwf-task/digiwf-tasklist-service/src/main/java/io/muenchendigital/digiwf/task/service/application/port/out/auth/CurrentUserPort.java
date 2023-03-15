package io.muenchendigital.digiwf.task.service.application.port.out.auth;

import io.holunda.polyflow.view.auth.User;

/**
 * Adapter to retrieve currently logged-in user information.
 */
public interface CurrentUserPort {
    /**
     * Retrieves current user.
     * @return current user.
     */
    User getCurrentUser();
}
