package de.muenchen.oss.digiwf.adapter.out.engine;

import de.muenchen.oss.digiwf.application.port.out.EngineAuthorizationsOutPort;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class EngineAdapter implements EngineAuthorizationsOutPort {
    private final AuthorizationService authorizationService;

    @NonNull
    @Override
    @Cacheable(EngineCacheConfiguration.OPTIMIZE_AUTH_CACHE)
    public List<Group> getOptimizeAuthorizedGroups() {
        log.info("Loading optimize authorized groups");
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
