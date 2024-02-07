package de.muenchen.oss.digiwf.task.correlator;

import de.muenchen.oss.digiwf.task.TaskExternalReference;
import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesCorrelator;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.camunda.bpm.engine.variable.Variables.createVariables;

/**
 * Enriches all processes with values contained in TASK_EXTERNAL_LINKS.
 */
@Component
public class GlobalProcessVariableCorrelator extends ProcessVariablesCorrelator {
  @NonNull
  @Override
  public VariableMap correlateVariables(@NonNull String processDefinitionKey, @NonNull String taskDefinitionKey, @NonNull VariableMap variables) {
    val correlations = createVariables();
    if (variables.containsKey(TaskVariables.TASK_EXTERNAL_LINKS.getName())) {
      val reader = CamundaBpmData.reader(variables);
      List<TaskExternalReference> references = reader.get(TaskVariables.TASK_EXTERNAL_LINKS);
      for (TaskExternalReference ref : references) {
        // we have to invert the values towards id -> type
        correlations.putValue(ref.getIdentity(), ref.getType());
      }
    }
    return correlations;
  }
}
