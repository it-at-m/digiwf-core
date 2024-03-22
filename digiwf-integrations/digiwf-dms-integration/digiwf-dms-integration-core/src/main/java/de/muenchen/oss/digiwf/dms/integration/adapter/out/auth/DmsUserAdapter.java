package de.muenchen.oss.digiwf.dms.integration.adapter.out.auth;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserOutPort;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DmsUserAdapter implements DmsUserOutPort {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public String getDmsUser() {
        return userAuthenticationProvider.getLoggedInUser();
    }
}
