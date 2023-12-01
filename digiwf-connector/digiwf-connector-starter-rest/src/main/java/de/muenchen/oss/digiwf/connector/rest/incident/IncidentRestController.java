/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.connector.rest.incident;

import de.muenchen.oss.digiwf.connector.api.incident.IncidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        this.incidentService.createIncident(dto.getProcessInstanceId(), dto.getMessageName(), dto.getMessageContent());
        return ResponseEntity.ok().build();
    }

}
