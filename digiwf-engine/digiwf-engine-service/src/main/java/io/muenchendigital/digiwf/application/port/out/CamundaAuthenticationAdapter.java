package io.muenchendigital.digiwf.application.port.out;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CamundaAuthenticationAdapter implements AuthenticationPort {

    private final IdentityService identityService;

    @Override
    public void setAuthentication(String userId, List<String> groups) {
        identityService.setAuthentication(userId, groups);
    }

    @Override
    public void clearAuthentication() {
        identityService.clearAuthentication();
    }
}
