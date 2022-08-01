/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.connectorrest.message;

import io.muenchendigital.digiwf.connector.api.message.MessageService;
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
 * Rest API to handle messages.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@RequestMapping("/rest/message")
@RequiredArgsConstructor
@Tag(name = "MessageApi", description = "API to handle messages")
public class MessageRestController {

    private final MessageService messageService;

    /**
     * Correlate a message
     *
     * @param dto message correlation data
     */
    @PostMapping
    @Operation(description = "correlate message")
    public ResponseEntity<Void> correlateMessage(@RequestBody @Valid final CorrelateMessageDto dto) {
        this.messageService.correlateMessage(dto);
        return ResponseEntity.ok().build();
    }

}
