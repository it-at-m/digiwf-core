package io.muenchendigital.digiwf.connector.security.internal;

import io.muenchendigital.digiwf.connector.security.api.UserContext;
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
