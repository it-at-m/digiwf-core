/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.api.resource;

import io.muenchendigital.digiwf.humantask.api.mapper.HumanTaskApiMapper;
import io.muenchendigital.digiwf.humantask.api.transport.*;
import io.muenchendigital.digiwf.humantask.domain.service.HumanTaskService;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * API to load user tasks and perform actions on them.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/task")
@RequiredArgsConstructor
@Tag(name = "HumanTaskRestController", description = "API to load human tasks and perform actions on them")
public class HumanTaskRestController {

    private final HumanTaskService taskService;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final HumanTaskApiMapper taskMapper;

    /**
     * Returns all tasks assigned to the authenticated user.
     *
     * @return tasks
     */
    @GetMapping
    public ResponseEntity<List<HumanTaskTO>> getTasks() {
        val tasks = this.taskService.getTasksForUser(this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.taskMapper.map2TO(tasks));
    }

    /**
     * Returns all group tasks of the authenticated user.
     *
     * @return tasks
     */
    @GetMapping("/group/open")
    public ResponseEntity<List<HumanTaskTO>> getOpenGroupTasks() {
        val tasks = this.taskService.getOpenGroupTasks(this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(this.taskMapper.map2TO(tasks));
    }

    /**
     * Returns all group tasks of the authenticated user that are assigned.
     *
     * @return tasks
     */
    @GetMapping("/group/assigned")
    public ResponseEntity<List<HumanTaskTO>> getAssignedGroupTasks() {
        val tasks = this.taskService.getAssignedGroupTasks(this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(this.taskMapper.map2TO(tasks));
    }

    /**
     * Return the detail representation object of a task
     *
     * @param taskId Id of the tasks
     * @return task
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HumanTaskDetailTO> getTaskDetail(@PathVariable("id") @NotBlank final String taskId) {
        val task = this.taskService.getDetail(taskId, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(this.taskMapper.map2TO(task));
    }

    /**
     * Saves the given data to the task.
     *
     * @param saveTO Data of the task
     * @return the saved task
     */
    @PutMapping
    public ResponseEntity<HumanTaskDetailTO> saveTask(@Valid @RequestBody final SaveTO saveTO) {
        this.taskService.saveTask(saveTO.getTaskId(), saveTO.getVariables(), this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Completes the given task
     *
     * @param completeTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> completeTask(@Valid @RequestBody final CompleteTO completeTO) {
        this.taskService.completeTask(completeTO.getTaskId(), completeTO.getVariables(), this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Set a follow up date to the given task.
     *
     * @param followUpTO the follow up
     */
    @PostMapping("/followup")
    public ResponseEntity<Void> followUpTask(@Valid @RequestBody final FollowUpTO followUpTO) {
        this.taskService.followUp(followUpTO.getTaskId(), followUpTO.getFollowUpDate(), this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Assigns the task to the authenticated user.
     *
     * @param taskId Id of the task
     */
    @PostMapping("/assign/{id}")
    public ResponseEntity<Void> assignTask(@PathVariable("id") final String taskId) {
        this.taskService.assignTask(taskId, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }

    /**
     * Cancels the task.
     *
     * @param taskId Id of the task
     */
    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelTask(@PathVariable("id") final String taskId) {
        this.taskService.cancelTask(taskId, this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

}

