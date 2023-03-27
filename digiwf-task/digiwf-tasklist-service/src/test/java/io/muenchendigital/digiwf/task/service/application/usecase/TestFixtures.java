package io.muenchendigital.digiwf.task.service.application.usecase;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.taskpool.api.task.ProcessReference;
import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.TaskVariables;
import io.muenchendigital.digiwf.task.service.domain.JsonSchema;
import lombok.val;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
        "Task Name" ,
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
}
