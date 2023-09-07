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
}
