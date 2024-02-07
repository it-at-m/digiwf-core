package de.muenchen.oss.digiwf.task.service.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import de.muenchen.oss.digiwf.task.TaskSchemaType;
import de.muenchen.oss.digiwf.task.TaskVariables;
import de.muenchen.oss.digiwf.task.service.domain.JsonSchema;
import de.muenchen.oss.digiwf.task.service.domain.legacy.Form;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.taskpool.api.task.ProcessReference;
import io.holunda.camunda.taskpool.api.task.TaskCreatedEngineEvent;
import io.holunda.camunda.taskpool.api.task.TaskDeletedEngineEvent;
import io.holunda.camunda.variable.serializer.VariableSerializerKt;
import io.holunda.polyflow.view.Task;
import lombok.SneakyThrows;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestFixtures {

    public static final VariableFactory<String> STRING_VAL = stringVariable("string_val");
    public static final VariableFactory<Integer> INTEGER_VAL = intVariable("int_val");

    private static final Instant createTime = Instant.now();
    private static final String instanceId = UUID.randomUUID().toString();
    public static VariableMap variables = CamundaBpmData.builder()
            .set(TaskVariables.TASK_SCHEMA_KEY, "schema-1")
            .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
            .build();

    public static VariableMap legacyFormVariables = CamundaBpmData.builder()
            .set(TaskVariables.TASK_SCHEMA_KEY, "form-1")
            .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.VUETIFY_FORM_BASE)
            .build();

    public static JsonSchema generateSchema(String schemaId) {
        return new JsonSchema(schemaId, "{\"type\": \"object\"}");
    }

    public static Form generateForm(String formid) {
        val stringVarFormField = new Form.FormField();
        stringVarFormField.setKey("STRING_VAR");
        stringVarFormField.setType("string");

        val form = mock(Form.class);
        when(form.getFormFieldMap()).thenReturn(Map.ofEntries(
                Map.entry("STRING_VAR", stringVarFormField)
        ));
        when(form.getKey()).thenReturn(formid);

        return form;
    }

    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate) {
        return generateTask(taskId, candidateUsers, candidateGroups, assignee, followUpDate, false);
    }

    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate, Boolean cancelable) {
        return generateTask(taskId, candidateUsers, candidateGroups, assignee, followUpDate, cancelable, variables);
    }


    /**
     * Generates a task.
     * @param taskId task id.
     * @param candidateUsers set of candidate users.
     * @param candidateGroups set of candidate groups.
     * @param assignee assignee of a task, may be null.
     * @param followUpDate follow-up data, may be null.
     * @param cancelable cancelable flag.
     * @param variables variable map written with a help of Camunda BPM Data.
     * @return generated task.
     */
    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate, Boolean cancelable, VariableMap variables) {
        final ObjectMapper om = new ObjectMapper();
        try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {

        }

        // this emulates the serialization and de-serialization based on transfer of the variable from the engine to
        // task list, where the types get lost.
        val vars = VariableSerializerKt.toPayloadVariableMap(VariableSerializerKt.toPayloadJson(variables, om), om);

        val varsWriter = writer(vars);
        if (cancelable != null) {
            varsWriter.set(TaskVariables.TASK_CANCELABLE, cancelable);
        }

        val correlations = Variables.createVariables();
        if (variables.containsKey(TaskVariables.TASK_EXTERNAL_LINKS.getName())) {
            reader(variables).get(TaskVariables.TASK_EXTERNAL_LINKS).forEach(reference ->
                correlations.putValue(reference.getIdentity(), reference.getType())
            );
        }

        val reference = new ProcessReference(instanceId, instanceId, "def:1", "def", "Sample process", "app1", null);
        return new Task(
                taskId,
                reference,
                "task_def_1",
                varsWriter.variables(),
                correlations,
                null,
                "Task Name",
                null,
                null,
                50,
                createTime,
                candidateUsers,
                candidateGroups,
                assignee,
                null,
                null,
                followUpDate,
                false
        );
    }

    public static List<Task> generateTasks(int count, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, VariableMap variables) {
        val tasks = new ArrayList<Task>();
        for (int i = 0; i < count; i++) {
            tasks.add(generateTask("task_" + i, candidateUsers, candidateGroups, assignee, null, false, variables));
        }
        return tasks;
    }

    public static List<Task> generateTasks(int count, Set<String> candidateUsers, Set<String> candidateGroups, String assignee) {
        return generateTasks(count, candidateUsers, candidateGroups, assignee, variables);
    }

    public static TaskCreatedEngineEvent createEvent(Task task) {
        return new TaskCreatedEngineEvent(
                task.getId(),
                task.getSourceReference(),
                task.getTaskDefinitionKey(),
                task.getPayload(),
                task.getCorrelations(),
                task.getBusinessKey(),
                task.getName(),
                task.getDescription(),
                task.getFormKey(),
                task.getPriority(),
                task.getCreateTime() == null ? null : Date.from(task.getCreateTime()),
                task.getCandidateUsers(),
                task.getCandidateGroups(),
                task.getAssignee(),
                task.getOwner(),
                task.getDueDate() == null ? null : Date.from(task.getDueDate()),
                task.getFollowUpDate() == null ? null : Date.from(task.getFollowUpDate())
        );
    }

    public static TaskDeletedEngineEvent deleteEvent(Task task) {
        return new TaskDeletedEngineEvent(
                task.getId(),
                task.getSourceReference(),
                task.getTaskDefinitionKey(),
                task.getPayload(),
                task.getCorrelations(),
                task.getBusinessKey(),
                task.getName(),
                task.getDescription(),
                task.getFormKey(),
                task.getPriority(),
                task.getCreateTime() == null ? null : Date.from(task.getCreateTime()),
                task.getCandidateUsers(),
                task.getCandidateGroups(),
                task.getAssignee(),
                task.getOwner(),
                task.getDueDate() == null ? null : Date.from(task.getDueDate()),
                task.getFollowUpDate() == null ? null : Date.from(task.getFollowUpDate()),
                "reason"
        );
    }

    public static TaskCreatedEngineEvent createEvent(String taskId, String assignee) {
        val task = generateTask(taskId, Sets.newHashSet(), Sets.newHashSet(), assignee, null);
        return createEvent(task);
    }

    @SneakyThrows
    public static String getJsonFromFile(String relativeFilename) {
        return Files.readString(Path.of("src", "test", "resources", "files", relativeFilename));
    }
}
