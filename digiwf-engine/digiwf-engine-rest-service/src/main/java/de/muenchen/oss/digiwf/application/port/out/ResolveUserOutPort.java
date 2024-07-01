package de.muenchen.oss.digiwf.application.port.out;

import de.muenchen.oss.digiwf.domain.User;
import org.springframework.lang.NonNull;

public interface ResolveUserOutPort {
    /**
     * Resolve username to user object with attributes.
     *
     * @param username Username to resolve.
     * @return User object with corresponding attributes.
     */
    User resolveUser(@NonNull final String username);
}
