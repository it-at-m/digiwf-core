/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.domain.mapper.HumanTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTask;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTaskDetail;
import io.muenchendigital.digiwf.humantask.domain.model.TaskInfo;
import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import io.muenchendigital.digiwf.service.definition.domain.service.ServiceDefinitionService;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import io.muenchendigital.digiwf.shared.exception.ObjectNotFoundException;
import io.muenchendigital.digiwf.humantask.process.ProcessTaskConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;
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

    private final ServiceDefinitionService processDefinitionService;
    private final UserService userService;
    private final HumanTaskDataService humanTaskDataService;
    private final TaskInfoService taskInfoService;

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
     *
     * @param userId Id of the user
     * @return The tasks
     */
    public List<HumanTask> getTasksForUser(final String userId) {
        val tasks = this.taskService.createTaskQuery()
                .taskAssignee(userId)
                .list();
        return this.getHumanTasks(tasks);
    }

    /**
     * Returns the group tasks for the given userId and groups that are not assigned.
     *
     * @param userId Id of the user
     * @param groups Assigned groups of the user
     * @return The open group tasks
     */
    public List<HumanTask> getOpenGroupTasks(final String userId, final List<String> groups) {
        log.debug("getOpenGroupTasks: user {}", userId);

        val tasks = this.taskService.createTaskQuery()
                .taskCandidateUser(userId)
                .taskUnassigned()
                .list();

        val taskMap = tasks.stream().collect(Collectors.toMap(Task::getId, t -> t));
        val groupTasks = this.queryTaskByCandidateGroup(groups, false);
        groupTasks.stream().filter(task -> !taskMap.containsKey(task.getId())).forEach(tasks::add);
        return this.getHumanTasks(tasks);
    }

    /**
     * Returns the group tasks for the given userId and groups that are assigned.
     *
     * @param userId Id of the user
     * @param groups Assigned groups of the user
     * @return The assigned group tasks
     */
    public List<HumanTask> getAssignedGroupTasks(final String userId, final List<String> groups) {
        log.debug("getAssignedGroupTasks: user {}", userId);

        val tasks = this.taskService.createTaskQuery()
                .taskCandidateUser(userId)
                .includeAssignedTasks()
                .taskAssigned()
                .list();

        val taskMap = tasks.stream().collect(Collectors.toMap(Task::getId, t -> t));
        val groupTasks = this.queryTaskByCandidateGroup(groups, true);
        groupTasks.stream().filter(task -> !taskMap.containsKey(task.getId())).forEach(tasks::add);
        return this.getHumanTasks(tasks);
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

    //--------------------------------------------------------------- helper methods ---------------------------------------------------------------//

    private List<HumanTask> getHumanTasks(final List<Task> tasks) {
        log.debug("Found {} tasks", tasks.size());

        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }

        final List<String> taskIds = tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        final Map<String, TaskInfo> taskInfos = this.taskInfoService.getTaskInfoMapByTaskIds(taskIds);

        // If a camunda task does not exist in the dwf task info table log an error
        // See: https://wiki.muenchen.de/betriebshandbuch/index.php/DigiWF#Backend -> Task is missing in TaskInfo database table
        taskIds.stream()
                .filter(taskId -> !taskInfos.containsKey(taskId))
                .forEach(taskId -> log.error("Task with id {} is missing in TaskInfo database table", taskId));

        return tasks.stream()
                .filter(task -> taskInfos.containsKey(task.getId()))
                .map(task -> this.humanTaskMapper.map2Model(task, taskInfos.get(task.getId())))
                .collect(Collectors.toList());
    }

    //TODO create a HumanTask Access Service for the following methods

    private List<Task> queryTaskByCandidateGroup(final List<String> ous, final boolean assigned) {
        // select assigned OR unassigned tasks
        final String assigneeExpression = assigned ? "T1.ASSIGNEE_ IS NOT NULL" : "T1.ASSIGNEE_ IS NULL";

        final String query = "SELECT * "
                + "FROM ACT_RU_TASK T1"
                + " WHERE " + assigneeExpression
                + " AND T1.ID_ IN ("
                + "SELECT TASK_ID_"
                + " FROM ACT_RU_IDENTITYLINK I1"
                + " WHERE I1.TYPE_ = 'candidate'"
                + "AND (" + ous.stream().map(ou -> "lower(I1.GROUP_ID_) = lower('" + ou + "')").collect(Collectors.joining(" OR ")) + ")"
                + ")";

        return this.taskService.createNativeTaskQuery()
                .sql(query)
                .list();
    }

}
