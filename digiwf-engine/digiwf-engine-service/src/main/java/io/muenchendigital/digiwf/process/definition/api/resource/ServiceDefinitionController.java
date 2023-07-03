/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.definition.api.resource;

import io.muenchendigital.digiwf.process.definition.api.mapper.ServiceDefinitionApiMapper;
import io.muenchendigital.digiwf.process.definition.api.transport.ServiceDefinitionDetailTO;
import io.muenchendigital.digiwf.process.definition.api.transport.ServiceDefinitionTO;
import io.muenchendigital.digiwf.process.definition.api.transport.StartInstanceTO;
import io.muenchendigital.digiwf.process.definition.domain.facade.ServiceDefinitionFacade;
import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinition;
import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinitionDetail;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    public Page<ServiceDefinitionTO> getServiceDefinitions(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0)  final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        final Page<ServiceDefinition> definitions = this.serviceDefinitionFacade.getStartableServiceDefinitions(
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups(),
                page,
                size,
                query
        );
        return definitions.map(this.serviceDefinitionApiMapper::map2TO);
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
