/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.connector.rest.bpmnerror;

import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorService;
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
 * Rest API to handle bpmn errors.
 *
 * @author martind260
 */
@Validated
@RestController
@RequestMapping("/rest/bpmnerror")
@RequiredArgsConstructor
@Tag(name = "BpmnErrorApi", description = "API to handle bpmn errors")
public class BpmnErrorRestController {

    private final BpmnErrorService bpmnErrorService;

    /**
     * Create a bpmn error
     *
     * @param dto error correlation data
     */
    @PostMapping
    @Operation(description = "create bpmn error")
    public ResponseEntity<Void> createBpmnError(@RequestBody @Valid final BpmnErrorDto dto) {
        this.bpmnErrorService.createBpmnError(dto);
        return ResponseEntity.ok().build();
    }

}
