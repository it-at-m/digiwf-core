/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.domain.service;

import de.muenchen.oss.digiwf.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle HumanTasks in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HumanTaskService {

    //Camunda Services
    private final TaskService taskService;


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
    private boolean hasAccess(final Task task, final String userId, final List<String> groups) {
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
}
