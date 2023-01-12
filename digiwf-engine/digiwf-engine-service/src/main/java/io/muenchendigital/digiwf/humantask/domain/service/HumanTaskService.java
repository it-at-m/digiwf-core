/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.domain.mapper.ActRuTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.mapper.HumanTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTask;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTaskDetail;
import io.muenchendigital.digiwf.humantask.domain.model.TaskInfo;
import io.muenchendigital.digiwf.humantask.infrastructure.repository.ActRuGroupTaskSearchRepository;
import io.muenchendigital.digiwf.humantask.infrastructure.repository.ActRuTaskSearchRepository;
import io.muenchendigital.digiwf.humantask.process.ProcessTaskConstants;
import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import io.muenchendigital.digiwf.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service to handle HumanTasks in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HumanTaskService {
    private final HumanTaskDataService humanTaskDataService;
    private final TaskInfoService taskInfoService;
    private final ActRuTaskMapper actRuTaskMapper;
    private final ActRuGroupTaskSearchRepository actRuGroupTaskSearchRepository;
    private final ActRuTaskSearchRepository actRuTaskSearchRepository;

    //outdated form handling
    private final FormService formService;

    private final JsonSchemaService jsonSchemaService;

    //Camunda Services
    private final TaskService taskService;

    //Mapper
    private final HumanTaskMapper humanTaskMapper;

    /**
     * Loads the task data.
     *
     * @param taskId The Id of the Task
     * @param userId The Id of the User
     * @param groups The groups of the User
     * @return The Task with Detail Information
     */
    public HumanTaskDetail getDetail(final String taskId, final String userId, final List<String> groups) {
        final Task task = this.getTask(taskId);
        if (!this.hasAccess(task, userId, groups)) {
            throw new IllegalResourceAccessException(String.format("Task with id %s not accessable", taskId));
        }

        final Map<String, Object> variables = this.humanTaskDataService.getVariablesForTask(task);
        final TaskInfo taskInfo = this.taskInfoService.findByTaskId(taskId);

        final HumanTaskDetail detail = this.humanTaskMapper.map2Model(task, taskInfo, variables);

        final Optional<Form> form = this.formService.getForm(task.getFormKey());
        form.ifPresent(detail::setForm);

        if (form.isEmpty()) {
            final JsonSchema schema = this.humanTaskDataService.getSchemaKey(task.getId())
                    .map(this.jsonSchemaService::getByKey)
                    .orElseThrow()
                    .orElseThrow();

            detail.setJsonSchema(schema.getSchemaMap());
            detail.setStatusDocument(this.humanTaskDataService.getVariable(task.getId(), ProcessTaskConstants.TASK_STATUS_DOKUMENT).map(Boolean::valueOf).orElse(false));
        }
        return detail;
    }

    /**
     * Loads the task.
     *
     * @param id The Id of the Task
     * @return The Task
     */
    public Task getTask(final String id) {
        val task = this.taskService.createTaskQuery().taskId(id).initializeFormKeys().singleResult();
        if (task == null) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", id));
        }
        return task;
    }

    /**
     * Returns the assigned tasks for the given userId
     * In case of a missing task info there is an incomplete page content. The number of total items are the number of ActRuTask items.
     *
     * @param userId
     * @param query
     * @param followUp
     * @param pageable
     * @return
     */
    public Page<HumanTask> getTasksForUser(final String userId, @Nullable final String query, final Boolean followUp, final Pageable pageable) {
        return this.getActRuTaskEntityByAssigneeId(userId, query, followUp, pageable).map(this.humanTaskMapper::map2Model);
    }

    /**
     * Returns the group tasks for the given userId and groups that are not assigned.
     *
     * @param userId Id of the user
     * @param groups Assigned groups of the user
     * @return The open group tasks
     */
    public Page<HumanTask> getOpenGroupTasks(final String userId, final List<String> groups, @Nullable final String query, final Pageable pageable) {
        return this.getGroupTasks(userId, groups, false, query, pageable).map(this.humanTaskMapper::map2Model);
    }

    /**
     * Returns the group tasks for the given userId and groups that are assigned.
     *
     * @param userId
     * @param groups
     * @param query
     * @param pageable
     * @return
     */
    public Page<HumanTask> getAssignedGroupTasks(final String userId, final List<String> groups, @Nullable final String query, final Pageable pageable) {
        return this.getGroupTasks(userId, groups, true, query, pageable).map(this.humanTaskMapper::map2Model);
    }

    /**
     * Complets the task with the given variables..
     *
     * @param taskId    Id of the task the should be completed
     * @param variables Variables that are set during completion
     * @param userId    Id of the user that complets the task
     */
    public void completeTask(final String taskId, final Map<String, Object> variables, final String userId) {
        val task = this.getTask(taskId);

        if (!userId.equals(task.getAssignee())) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }

        final Map<String, Object> filteredVariables = this.humanTaskDataService.serializeGivenVariables(task, variables);
        this.taskService.complete(taskId, filteredVariables);
        log.info("task completed: {}", taskId);
    }

    /**
     * Saves the task with the given variables
     *
     * @param taskId    Id of the tasks that should be saved
     * @param variables Variables that should be saved
     * @param userId    Id of the user that saves the task
     */
    public void saveTask(final String taskId, final Map<String, Object> variables, final String userId) {
        val task = this.getTask(taskId);

        if (!userId.equals(task.getAssignee())) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }

        final Map<String, Object> filteredVariables = this.humanTaskDataService.serializeGivenVariables(task, variables);
        this.taskService.setVariables(taskId, filteredVariables);
    }

    /**
     * Assigns the task to the given user.
     *
     * @param taskId Id of the tasks that should be assinged
     * @param userId Id of user
     * @param groups Groups of the user
     */
    public void assignTask(final String taskId, final String userId, final List<String> groups) {
        val task = Optional.ofNullable(this.taskService.createTaskQuery().taskId(taskId).singleResult())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Task with the id %s is not available", taskId)));

        if (!this.hasAccess(task, userId, groups)) {
            throw new IllegalArgumentException(String.format("Task with id %s not available", taskId));
        }

        if (!StringUtils.isBlank(task.getAssignee())) {
            //            throw new IllegalArgumentException(String.format("Task with the id %s is already assigned", taskId));
            if (task.getAssignee().equals(userId)) {
                log.debug("Task {} is already assigned to {}.", taskId, userId);
                return;
            }
        }
        this.taskService.setAssignee(taskId, userId);
    }

    /**
     * Sets the follow up date for the given task.
     *
     * @param taskId       Id of the task
     * @param followUpDate Date to follow up
     * @param userId       Id of the users that sets the date
     */
    public void followUp(final String taskId, final String followUpDate, final String userId) {
        val task = this.getTask(taskId);

        if (!userId.equals(task.getAssignee())) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }

        if (StringUtils.isBlank(followUpDate)) {
            task.setFollowUpDate(null);
        } else {
            task.setFollowUpDate(Date.valueOf(followUpDate));
        }
        this.taskService.saveTask(task);
    }

    /**
     * Cancels the given task
     *
     * @param taskId Id of the task that should be canceled
     * @param userId Id of the user
     */
    public void cancelTask(final String taskId, final String userId) {
        val task = Optional.ofNullable(this.taskService.createTaskQuery().taskId(taskId).singleResult())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Die Aufgabe mit der id %s ist nicht vorhanden", taskId)));
        if (!userId.equals(task.getAssignee())) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }
        this.taskService.handleBpmnError(task.getId(), "default_error_code");
    }

    /**
     * Check if a user has access to a task
     *
     * @param taskId Id of the task whose access is to be checked
     * @param userId Id of the user
     * @param groups Groups of the user
     * @return has access
     */
    public boolean hasAccess(final String taskId, final String userId, final List<String> groups) {
        final Task task = this.getTask(taskId);
        return this.hasAccess(task, userId, groups);
    }

    /**
     * Check if a user has access to a task
     *
     * @param task   Task whose access is to be checked
     * @param userId Id of the user
     * @param groups Groups of the user
     * @return has access
     */
    public boolean hasAccess(final Task task, final String userId, final List<String> groups) {
        if (userId.equals(task.getAssignee())) {
            return true;
        }

        val identityLinks = this.taskService.getIdentityLinksForTask(task.getId());
        return identityLinks.stream()
                .filter(link -> IdentityLinkType.CANDIDATE.equals(link.getType()))
                .anyMatch(link -> groups.stream().anyMatch(group ->
                        StringUtils.isNoneBlank(link.getGroupId()) && group.equalsIgnoreCase(link.getGroupId()))
                        || userId.equals(link.getUserId()));
    }

    private Page<ActRuTask> getActRuTaskEntityByAssigneeId(final String assigneeId, @Nullable final String query, final Boolean followUp, final Pageable pageable) {
        return this.actRuTaskSearchRepository.search(assigneeId, query, followUp, pageable).map(actRuTaskMapper::map2Model);
    }
    private Page<ActRuTask> getGroupTasks(final String userId, final List<String> groups, final Boolean assigned, @Nullable final String query, final Pageable pageable) {
        final List<String> lowerCaseGroups = groups.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return this.actRuGroupTaskSearchRepository.search(userId, lowerCaseGroups, query, assigned, pageable).map(actRuTaskMapper::map2Model);
    }
}
