/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.input.message.api.resource;

import io.muenchendigital.digiwf.input.message.api.transport.SendMessageTO;
import io.muenchendigital.digiwf.input.message.domain.service.MessageService;
import io.muenchendigital.digiwf.service.instance.domain.service.ServiceInstanceService;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API for message inputs.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/input/message")
@RequiredArgsConstructor
@Tag(name = "MessageController", description = "API to interact with messages")
public class MessageController {

    private final ServiceInstanceService processInstanceService;
    private final AppAuthenticationProvider authenticationProvider;

    private final MessageService messageService;


    /**
     * Request to send a specific message.
     *
     * @param to Correlation to
     */
    @PostMapping("/send/message")
    public ResponseEntity<Void> sendMessage(@RequestBody final SendMessageTO to) {
        this.messageService.sendMessage(to.getInstanceId(), to.getMessage(), this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }

}
