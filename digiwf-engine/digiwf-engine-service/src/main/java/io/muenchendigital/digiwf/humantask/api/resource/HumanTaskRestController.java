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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.QueryParam;


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
     * Returns a page  tasks assigned to the authenticated user.
     *
     * @return tasks
     */
    @GetMapping
    public Page<HumanTaskTO> getTasks(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0)  final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query,
            @RequestParam(value="followUp", defaultValue = "false",required = false) final Boolean followUp
            ) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        return this.taskService.getTasksForUser(this.authenticationProvider.getCurrentUserId(), query, followUp, pageable).map(this.taskMapper::map2TO);
    }

    /**
     * Returns all group tasks of the authenticated user.
     *
     * @return tasks
     */
    @GetMapping("/group/open")
    public Page<HumanTaskTO> getOpenGroupTasks(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0)  final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        return this.taskService.getOpenGroupTasks(this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups(), query, pageable).map(this.taskMapper::map2TO);
    }

    /**
     * Returns all group tasks of the authenticated user that are assigned.
     *
     * @return tasks
     */
    @GetMapping("/group/assigned")
    public Page<HumanTaskTO> getAssignedGroupTasks(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0)  final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        return this.taskService.getAssignedGroupTasks(this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups(), query, pageable).map(this.taskMapper::map2TO);
    }

    /**
     * Return the detail representation object of a task
     *
     * @param taskId Id of the tasks
     * @return task
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HumanTaskDetailTO getTaskDetail(@PathVariable("id") @NotBlank final String taskId) {
        val task = this.taskService.getDetail(taskId, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return this.taskMapper.map2TO(task);
    }

    /**
     * Saves the given data to the task.
     *
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
     * @param completeTO
     * @return
     */
    @PostMapping
    public void completeTask(@Valid @RequestBody final CompleteTO completeTO) {
        this.taskService.completeTask(completeTO.getTaskId(), completeTO.getVariables(), this.authenticationProvider.getCurrentUserId());
    }

    /**
     * Set a follow up date to the given task.
     *
     * @param followUpTO the follow up
     */
    @PostMapping("/followup")
    public void followUpTask(@Valid @RequestBody final FollowUpTO followUpTO) {
        this.taskService.followUp(followUpTO.getTaskId(), followUpTO.getFollowUpDate(), this.authenticationProvider.getCurrentUserId());
    }

    /**
     * Assigns the task to the authenticated user.
     *
     * @param taskId Id of the task
     */
    @PostMapping("/assign/{id}")
    public void assignTask(@PathVariable("id") final String taskId) {
        this.taskService.assignTask(taskId, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
    }

    /**
     * Cancels the task.
     *
     * @param taskId Id of the task
     */
    @PostMapping("/cancel/{id}")
    public void cancelTask(@PathVariable("id") final String taskId) {
        this.taskService.cancelTask(taskId, this.authenticationProvider.getCurrentUserId());
    }

}

