package io.muenchendigital.digiwf.task.service.application.usecase;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.application.port.in.WorkOnUserTask;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import io.muenchendigital.digiwf.task.service.application.port.out.cancellation.CancellationFlagOutPort;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.LegacyFormValidationPort;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.LegacyPayloadTaskCommandPort;
import io.muenchendigital.digiwf.task.service.application.port.out.engine.TaskCommandPort;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskNotFoundException;
import io.muenchendigital.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import io.muenchendigital.digiwf.task.service.application.port.out.schema.*;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;
import io.muenchendigital.digiwf.task.service.domain.TaskWithSchemaRef;
import io.muenchendigital.digiwf.task.service.domain.legacy.Form;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {
    private final TaskQueryPort taskQueryPort;
    private final CurrentUserPort currentUserPort;
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
    private final JsonSchemaPort jsonSchemaPort;
    private final TaskCommandPort taskCommandPort;
    private final LegacyPayloadTaskCommandPort legacyPayloadTasCommandPort;
    private final LegacyFormValidationPort legacySchemaValidationPort;
    private final JsonSchemaValidationPort jsonSchemaValidationPort;
    private final CancellationFlagOutPort cancellationFlagOutPort;
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort;

    @Override
    public JsonSchema loadSchema(String schemaId) throws JsonSchemaNotFoundException {
        return jsonSchemaPort.getSchemaById(schemaId);
    }

    @Override
    public TaskWithSchemaRef loadUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);

        val cancelable = cancellationFlagOutPort.apply(task);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val type = taskSchemaTypeResolverPort.apply(task);

        switch (type) {
            case VUETIFY_FORM_BASE:
                val form = legacyPayloadTasCommandPort.loadFormById(schemaRef);
                this.filterLegacyFormBased(task, form);
                break;
            case SCHEMA_BASED:
                val schema = jsonSchemaPort.getSchemaById(schemaRef);
                this.filterSchemaBased(task, schema);
            default:
                break;
        }

        return new TaskWithSchemaRef(task, schemaRef, cancelable, type);
    }

    @Override
    public TaskWithSchema loadUserTaskWithSchema(String taskId) throws TaskNotFoundException, JsonSchemaNotFoundException {
        val task = getTaskForUser(taskId);

        val cancelable = cancellationFlagOutPort.apply(task);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val type = taskSchemaTypeResolverPort.apply(task);
        switch (type) {
            case VUETIFY_FORM_BASE:
                val form = legacyPayloadTasCommandPort.loadFormById(schemaRef);
                this.filterLegacyFormBased(task, form);
                return new TaskWithSchema(task, cancelable, type, null, form);
            case SCHEMA_BASED:
            default:
                val schema = jsonSchemaPort.getSchemaById(schemaRef);
                filterSchemaBased(task, schema);
                return new TaskWithSchema(task, cancelable, type, schema, null);
        }
    }

    @Override
    public void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        switch (taskSchemaTypeResolverPort.apply(task)) {
            case VUETIFY_FORM_BASE:
                legacyPayloadTasCommandPort.completeOldSchemaUserTask(taskId, payload);
                break;
            case SCHEMA_BASED:
            default:
                val schemaRef = taskSchemaRefResolverPort.apply(task);
                val schema = jsonSchemaPort.getSchemaById(schemaRef);
                val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
                taskCommandPort.completeUserTask(taskId, variables);
                break;
        }
    }

    @Override
    public void saveUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        switch (taskSchemaTypeResolverPort.apply(task)) {
            case VUETIFY_FORM_BASE:
                legacyPayloadTasCommandPort.saveOldSchemaUserTask(taskId, payload);
                break;
            case SCHEMA_BASED:
            default:
                val schemaRef = taskSchemaRefResolverPort.apply(task);
                val schema = jsonSchemaPort.getSchemaById(schemaRef);
                val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
                taskCommandPort.saveUserTask(taskId, variables);
                break;
        }
    }

    @Override
    public void assignUserTask(String taskId, String assignee) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (!assignee.equals(task.getAssignee())) {
            taskCommandPort.assignUserTask(taskId, assignee);
        }
    }

    @Override
    public void unassignUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (task.getAssignee() != null) {
            taskCommandPort.unassignUserTask(taskId);
        }
    }

  @Override
  public void deferUserTask(String taskId, OffsetDateTime followUpDate) throws TaskNotFoundException {
    getTaskForUser(taskId); // check if task exists, otherwise throw an exception
    taskCommandPort.deferUserTask(taskId, followUpDate.toInstant());
  }

    @Override
    public void undeferUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (task.getFollowUpDate() != null) {
            taskCommandPort.undeferUserTask(taskId);
        }
    }

    @Override
    public void cancelUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (cancellationFlagOutPort.apply(task)) {
            taskCommandPort.cancelUserTask(taskId);
        } else {
            throw new IllegalArgumentException("Task " + taskId + " can not be cancelled.");
        }
    }

    private Task getTaskForUser(String taskId) {
        val currentUser = currentUserPort.getCurrentUser();
        return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    }

    private void filterSchemaBased(Task task, JsonSchema schema) {
        val filteredPayload = this.jsonSchemaValidationPort.filterVariables(task.getPayload(), schema);
        task.getPayload().clear();
        task.getPayload().putAll(filteredPayload);
    }

    private void filterLegacyFormBased(Task task, Form form) {
        val filteredPayload = legacySchemaValidationPort.filterVariables(task.getPayload(), form);
        task.getPayload().clear();
        task.getPayload().putAll(filteredPayload);
    }

}
