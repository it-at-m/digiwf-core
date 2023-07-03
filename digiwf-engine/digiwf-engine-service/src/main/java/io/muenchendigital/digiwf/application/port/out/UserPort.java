package io.muenchendigital.digiwf.application.port.out;

import io.muenchendigital.digiwf.legacy.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {

    Optional<User> findByUsername(String username);

    List<String> getGroups(final String userId);


}
