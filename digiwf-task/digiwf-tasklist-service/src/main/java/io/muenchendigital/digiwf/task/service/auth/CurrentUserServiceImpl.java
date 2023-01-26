package io.muenchendigital.digiwf.task.service.auth;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.auth.User;
import org.springframework.stereotype.Component;

/**
 * Retrieves current user.
 */
@Component
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public User getCurrentUser() {
        // TODO: implement real user management and activate security.
        return new User("username", Sets.newHashSet("group1", "group2"));
    }
}
