package de.muenchen.oss.digiwf.task;

import io.holunda.camunda.taskpool.api.task.EngineTaskCommand;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RequiredArgsConstructor
public class TestHelper {

  private final RuntimeService runtimeService;
  private final TaskService taskService;
  private final String processDefinitionKey;
  private final ArgumentCaptor<List<Object>> commandCaptor;

  private final List<String> startedProcessInstances = new ArrayList<>();


  public List<EngineTaskCommand> commands() {
    return commandCaptor.getValue().stream().filter((it) -> it instanceof EngineTaskCommand).map(it -> (EngineTaskCommand) it).collect(Collectors.toList());
  }

  public EngineTaskCommand command() {
    val commands = commandCaptor.getValue();
    if (commands.size() != 1) {
      fail("Expected to get exactly command, but found %d, %s", commands.size(), commands);
    }
    return (EngineTaskCommand) commands.get(0);
  }


  public Task userTask() {
    return taskService.createTaskQuery().active().singleResult();
  }

  public void start(VariableMap variables) {
    ProcessInstance instance = runtimeService.startProcessInstanceByKey(
        processDefinitionKey,
        "itest",
        variables
    );
    assertThat(instance).isNotNull();
    startedProcessInstances.add(instance.getRootProcessInstanceId());
  }

  public void cleanup() {
    startedProcessInstances.forEach(id -> runtimeService.deleteProcessInstance(id, "stop"));
  }
}
