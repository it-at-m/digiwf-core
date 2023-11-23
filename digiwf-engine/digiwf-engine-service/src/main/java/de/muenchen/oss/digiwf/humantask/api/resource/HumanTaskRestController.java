/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.api.resource;

import de.muenchen.oss.digiwf.humantask.api.transport.*;
import de.muenchen.oss.digiwf.humantask.domain.service.HumanTaskService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * API for handle old process definition based tasks using new task service backend
 *
 * @deprecated
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/task")
@RequiredArgsConstructor
@Tag(name = "HumanTaskRestController", description = "API for handle old process definition based tasks using new task service backend")
public class HumanTaskRestController {

    private final HumanTaskService taskService;
    private final AppAuthenticationProvider authenticationProvider;

    /**
     * Saves the given data to the task.
     *
     * @deprecated
     * @param saveTO Data of the task
     * @return the saved task
     */
    @PutMapping
    public void saveTask(@Valid @RequestBody final SaveTO saveTO) {
        this.taskService.saveTask(saveTO.getTaskId(), saveTO.getVariables(), this.authenticationProvider.getCurrentUserId());
    }

    /**
     * Completes the given task
     *
     * @deprecated
     * @param completeTO
     * @return
     */
    @PostMapping
    public void completeTask(@Valid @RequestBody final CompleteTO completeTO) {
        this.taskService.completeTask(completeTO.getTaskId(), completeTO.getVariables(), this.authenticationProvider.getCurrentUserId());
    }
}

