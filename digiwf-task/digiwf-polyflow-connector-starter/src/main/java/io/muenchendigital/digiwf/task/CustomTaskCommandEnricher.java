package io.muenchendigital.digiwf.task;

import io.holunda.camunda.taskpool.api.task.TaskIdentityWithPayloadAndCorrelations;
import io.holunda.polyflow.taskpool.collector.task.TaskVariableLoader;
import io.holunda.polyflow.taskpool.collector.task.VariablesEnricher;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesCorrelator;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesFilter;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.impl.json.jackson.JacksonJsonNode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomTaskCommandEnricher implements VariablesEnricher {
    private final ProcessVariablesFilter processVariablesFilter;
    private final ProcessVariablesCorrelator processVariablesCorrelator;
    private final TaskVariableLoader taskVariableLoader;

    @NotNull
    @Override
    public <T extends TaskIdentityWithPayloadAndCorrelations> T enrich(@NotNull T command) {

        // load variables typed
        VariableMap variablesTyped = taskVariableLoader.getTypeVariables(command);
        variablesTyped = mapToData(variablesTyped);

        // Payload enrichment
        command.getPayload().putAll(
            processVariablesFilter.filterVariables(
                command.getSourceReference().getDefinitionKey(),
                command.getTaskDefinitionKey(),
                variablesTyped
            )
        );

        // Correlations
        command.getCorrelations().putAll(
            processVariablesCorrelator.correlateVariables(
                command.getSourceReference().getDefinitionKey(),
                command.getTaskDefinitionKey(),
                variablesTyped
            )
        );

        // Mark as enriched
        command.setEnriched(true);
        return command;
    }

    private VariableMap mapToData(final VariableMap variables) {
        final VariableMap data = Variables.createVariables();
        variables.forEach((key, value) -> {
            // JSON
            if (value instanceof JacksonJsonNode) {
                data.put(key, mapJsonToData((JacksonJsonNode) value));
            }
            // candidate user and groups
            else if (value instanceof ArrayList) {
                data.put(key, SerializationUtils.serialize((ArrayList) value));
            } else {
                data.put(key, value);
            }
        });
        return data;
    }

    byte[] mapJsonToData(final JacksonJsonNode object) {
        Object out;
        if (object.isArray()) {
            out = new JSONArray(object.toString()).toList();
        } else {
            out = new JSONObject(object.toString()).toMap();
        }
        return SerializationUtils.serialize(out);
    }
}

