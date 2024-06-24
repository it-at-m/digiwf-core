package de.muenchen.oss.digiwf.application.port.in;

import de.muenchen.oss.digiwf.domain.User;
import org.springframework.lang.NonNull;

public interface ResolveUserInPort {
    User resolveUser(@NonNull final String userName);
}
