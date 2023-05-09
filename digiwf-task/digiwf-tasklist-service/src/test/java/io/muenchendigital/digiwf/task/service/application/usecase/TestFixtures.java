package io.muenchendigital.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.taskpool.api.task.ProcessReference;
import io.holunda.camunda.taskpool.api.task.TaskCreatedEngineEvent;
import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskVariables;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;
import lombok.SneakyThrows;
import lombok.val;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static io.holunda.camunda.bpm.data.CamundaBpmData.intVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

public class TestFixtures {

  public static final VariableFactory<String> STRING_VAL = stringVariable("string_val");
  public static final VariableFactory<Integer> INTEGER_VAL = intVariable("int_val");

  private static final Instant createTime = Instant.now();
  private static final String instanceId = UUID.randomUUID().toString();

  public static JsonSchema generateSchema(String schemaId) {
    return new JsonSchema(schemaId, "{\"type\": \"object\"}");
  }

  public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate) {
    try {
      Thread.sleep(1);
    } catch (InterruptedException ignored) {

    }
    val variables = CamundaBpmData.builder()
        .set(TaskVariables.TASK_SCHEMA_KEY, "schema-1")
        .build();

    val reference = new ProcessReference(instanceId, instanceId, "def:1", "def", "Sample process", "app1", null);
    return new Task(
        taskId,
        reference,
        "task_def_1",
        variables,
        createVariables(),
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

  public static List<Task> generateTasks(int count, Set<String> candidateUsers, Set<String> candidateGroups, String assignee) {
    val tasks = new ArrayList<Task>();
    for (int i = 0; i < count; i++) {
      tasks.add(generateTask("task_" + i, candidateUsers, candidateGroups, assignee, null));
    }
    return tasks;
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

  public static TaskCreatedEngineEvent createEvent(String taskId, String assignee) {
    val task = generateTask(taskId, Sets.newHashSet(), Sets.newHashSet(), assignee, null);
    return createEvent(task);
  }

  @SneakyThrows
  public static String getJsonFromFile(String relativeFilename) {
    return Files.readString(Path.of("src", "test", "resources", "files", relativeFilename));
  }
}
