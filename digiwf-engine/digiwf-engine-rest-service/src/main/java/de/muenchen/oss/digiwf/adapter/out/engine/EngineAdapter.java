package de.muenchen.oss.digiwf.adapter.out.engine;

import de.muenchen.oss.digiwf.application.port.out.EngineAuthorizationsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@RequiredArgsConstructor
@Validated
public class EngineAdapter implements EngineAuthorizationsOutPort {
    private final AuthorizationService authorizationService;

    @NonNull
    @Override
    public List<Group> getOptimizeAuthorizedGroups() {
        return authorizationService.createAuthorizationQuery()
                .resourceType(Resources.APPLICATION)
                .hasPermission(Permissions.ACCESS)
                .list().stream().filter(
                        i -> i.getGroupId() != null &&
                                (i.getResourceId().equals("optimize") || i.getResourceId().equals("*"))
                )
                .map(i -> new Group(i.getGroupId()))
                .toList();
    }
}
