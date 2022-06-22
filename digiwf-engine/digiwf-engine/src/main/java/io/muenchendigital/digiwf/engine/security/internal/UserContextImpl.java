package io.muenchendigital.digiwf.engine.security.internal;

import io.muenchendigital.digiwf.engine.security.api.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserContextImpl implements UserContext {

    @Override
    public String getLoggedInUserId() {
        return null;
    }

    @Override
    public List<String> getUserGroups() {
        return null;
    }
}
