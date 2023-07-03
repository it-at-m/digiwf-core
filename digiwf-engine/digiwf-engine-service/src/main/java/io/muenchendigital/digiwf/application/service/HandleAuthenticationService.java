package io.muenchendigital.digiwf.application.service;

import io.muenchendigital.digiwf.application.port.in.HandleAuthenticationUseCase;
import io.muenchendigital.digiwf.application.port.out.AuthenticationPort;
import io.muenchendigital.digiwf.application.port.out.CurrentUserPort;
import io.muenchendigital.digiwf.application.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleAuthenticationService implements HandleAuthenticationUseCase {

    private final AuthenticationPort authenticationPort;
    private final UserPort userPort;
    private final CurrentUserPort currentUserPort;

    @Override
    public void initalizeAuthentication() {
        val username = currentUserPort.getLoggedInUsername();
        val roles = currentUserPort.getLoggedInUserRoles();
        val user = userPort.findByUsername(username);
        if (user.isPresent()) {
            val groups = userPort.getGroups(user.get().getLhmObjectId());
            val allGroups = new ArrayList<>(groups);
            allGroups.addAll(roles);
            log.debug("Accessing {} [ {} ]", username, allGroups);
            authenticationPort.setAuthentication(user.get().getLhmObjectId(), allGroups);
        } else {
            log.debug("Accessing {}  [ {} ]", username, roles);
            authenticationPort.setAuthentication(username, new ArrayList<>(roles));
        }
    }

    @Override
    public void clearAuthentication() {
        log.debug("Authentication cleared");
        this.authenticationPort.clearAuthentication();
    }
}
