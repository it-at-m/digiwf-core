/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.process.listener;

import io.muenchendigital.digiwf.humantask.domain.model.TaskInfoUpdate;
import io.muenchendigital.digiwf.humantask.domain.service.TaskInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * Listener that creates and updates the TaskInfoEntity
 *
 * @author externer.dl.horn
 * @deprecated due to new taskmanagement with polyflow
 */
@Slf4j
@Component
@Deprecated
@RequiredArgsConstructor
public class UserTaskInfoListener {

    private final TaskInfoService taskInfoService;

    @EventListener
    public void taskInfoListeners(final DelegateTask delegateTask) throws Exception {

        switch (delegateTask.getEventName()) {
            case "create":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoService.createTaskInfo(delegateTask);
                break;
            case "assignment":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoService.updateTaskInfo(delegateTask.getId(), new TaskInfoUpdate(delegateTask.getAssignee()));
                break;
            case "complete":
            case "delete":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoService.deleteTaskInfo(delegateTask.getId());
                break;
            default:
                break;
        }
    }
}
