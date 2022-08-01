/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.connectorrest.incident;

import io.muenchendigital.digiwf.connector.incident.api.IncidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest API to handle incidents.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@RequestMapping("/rest/incident")
@RequiredArgsConstructor
@Tag(name = "IncidentApi", description = "API to handle incidents")
public class IncidentRestController {

    private final IncidentService incidentService;

    /**
     * Create a incident
     *
     * @param dto incident creation data
     */
    @PostMapping
    @Operation(description = "create incident")
    public ResponseEntity<Void> createIncident(@RequestBody @Valid final CreateIncidentDto dto) {
        this.incidentService.createIncident(dto.getProcessInstanceId(), dto.getMessageName());
        return ResponseEntity.ok().build();
    }

}
