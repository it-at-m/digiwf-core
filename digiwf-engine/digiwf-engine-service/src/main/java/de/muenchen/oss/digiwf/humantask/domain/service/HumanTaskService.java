/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.domain.service;

import de.muenchen.oss.digiwf.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.Map;

import static de.muenchen.oss.digiwf.task.TaskVariables.TASK_ASSIGNEE;

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
     * Complets the task with the given variables..
     *
     * @deprecated
     * @param taskId    Id of the task the should be completed
     * @param variables Variables that are set during completion
     * @param userId    Id of the user that complets the task
     */
    @Deprecated
    public void completeTask(final String taskId, final Map<String, Object> variables, final String userId) {
        val task = this.getTask(taskId);
        checkTaskAccess(taskId, userId);

        final Map<String, Object> filteredVariables = this.humanTaskDataService.serializeGivenVariables(task, variables);
        this.taskService.complete(taskId, filteredVariables);
        log.info("task completed: {}", taskId);
    }


    /**
     * Saves the task with the given variables
     *
     * @deprecated
     * @param taskId    Id of the tasks that should be saved
     * @param variables Variables that should be saved
     * @param userId    Id of the user that saves the task
     */
    @Deprecated
    public void saveTask(final String taskId, final Map<String, Object> variables, final String userId) {
        val task = this.getTask(taskId);
        checkTaskAccess(taskId, userId);

        final Map<String, Object> filteredVariables = this.humanTaskDataService.serializeGivenVariables(task, variables);
        this.taskService.setVariables(taskId, filteredVariables);
        val updatedTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.saveTask(updatedTask);
    }

    private void checkTaskAccess(String taskId, String userId) {
        val assignedUserId = TASK_ASSIGNEE.from(taskService, taskId).getLocal();

        if (!userId.equals(assignedUserId)) {
            throw new ObjectNotFoundException(String.format("The task with the id %s is not available.", taskId));
        }
    }

}
