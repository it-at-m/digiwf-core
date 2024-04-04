package de.muenchen.oss.digiwf.application.port.out;

import org.springframework.lang.NonNull;

public interface UserToGroupResolverOutPort {

    boolean isUserInOptimizeGroup(@NonNull String username);
}
