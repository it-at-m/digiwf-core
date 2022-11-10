/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.definition.api.resource;

import io.muenchendigital.digiwf.service.definition.api.mapper.ServiceDefinitionApiMapper;
import io.muenchendigital.digiwf.service.definition.api.transport.ServiceDefinitionDetailTO;
import io.muenchendigital.digiwf.service.definition.api.transport.ServiceDefinitionTO;
import io.muenchendigital.digiwf.service.definition.api.transport.StartInstanceTO;
import io.muenchendigital.digiwf.service.definition.domain.facade.ServiceDefinitionFacade;
import io.muenchendigital.digiwf.service.definition.domain.model.ServiceDefinition;
import io.muenchendigital.digiwf.service.definition.domain.model.ServiceDefinitionDetail;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API to interact with service definitions.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/service/definition")
@RequiredArgsConstructor
@Tag(name = "ServiceDefinitionController", description = "API to interact with service definitions")
public class ServiceDefinitionController {

    private final ServiceDefinitionFacade serviceDefinitionFacade;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ServiceDefinitionApiMapper serviceDefinitionApiMapper;

    /**
     * Get all service definition.
     *
     * @return all service definitions
     */
    @GetMapping
    @Operation(description = "load all available service definitions")
    public ResponseEntity<List<ServiceDefinitionTO>> getServiceDefinitions() {
        final List<ServiceDefinition> definitions = this.serviceDefinitionFacade.getStartableServiceDefinitions(
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups()
        );
        return ResponseEntity.ok(this.serviceDefinitionApiMapper.map2TO(definitions));
    }

    /**
     * Get a detail object of the service definition.
     *
     * @param key key of the service
     * @return service definition
     */
    @GetMapping("/{key}")
    @Operation(description = "Get a specific service definition")
    public ResponseEntity<ServiceDefinitionDetailTO> getServiceDefinition(@PathVariable("key") final String key) {
        final ServiceDefinitionDetail definition = this.serviceDefinitionFacade
                .getServiceDefinitionDetail(key, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(this.serviceDefinitionApiMapper.map2TO(definition));
    }

    /**
     * Start the specified service.
     *
     * @param to data to start a service
     */
    @PostMapping()
    @Operation(description = "Start a specific service")
    public ResponseEntity<Void> startInstance(@RequestBody final StartInstanceTO to) {
        this.serviceDefinitionFacade.startInstance(to.getKey(), to.getVariables(), this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }

}
