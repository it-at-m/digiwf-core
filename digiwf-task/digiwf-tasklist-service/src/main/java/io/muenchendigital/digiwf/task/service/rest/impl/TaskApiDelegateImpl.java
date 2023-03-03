package io.muenchendigital.digiwf.task.service.rest.impl;

import io.muenchendigital.digiwf.task.service.rest.api.TaskApiDelegate;
import io.muenchendigital.digiwf.task.service.rest.model.TaskAssignmentTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskCombinedSchemaTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskDeferalTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskWithDetailsTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Task API delegate.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TaskApiDelegateImpl implements TaskApiDelegate {


    @Override
    public ResponseEntity<TaskCombinedSchemaTO> getSchema(String schemaId) {
        return TaskApiDelegate.super.getSchema(schemaId);
    }

    @Override
    public ResponseEntity<TaskCombinedSchemaTO> getTaskSchema(String taskId) {
        return TaskApiDelegate.super.getTaskSchema(taskId);
    }

    @Override
    public ResponseEntity<TaskWithDetailsTO> getTaskByTaskId(String taskId) {
        return TaskApiDelegate.super.getTaskByTaskId(taskId);
    }

    @Override
    public ResponseEntity<Void> completeTask(String taskId, Map<String, Object> requestBody) {
        return TaskApiDelegate.super.completeTask(taskId, requestBody);
    }

    @Override
    public ResponseEntity<Void> saveTaskVariables(String taskId, Map<String, Object> requestBody) {
        return TaskApiDelegate.super.saveTaskVariables(taskId, requestBody);
    }

    @Override
    public ResponseEntity<Void> assignTask(String taskId, TaskAssignmentTO taskAssignmentTO) {
        return TaskApiDelegate.super.assignTask(taskId, taskAssignmentTO);
    }

    @Override
    public ResponseEntity<Void> unassignTask(String taskId) {
        return TaskApiDelegate.super.unassignTask(taskId);
    }

    @Override
    public ResponseEntity<Void> deferTask(String taskId, TaskDeferalTO taskDeferalTO) {
        return TaskApiDelegate.super.deferTask(taskId, taskDeferalTO);
    }

    @Override
    public ResponseEntity<Void> undeferTask(String taskId) {
        return TaskApiDelegate.super.undeferTask(taskId);
    }
}
