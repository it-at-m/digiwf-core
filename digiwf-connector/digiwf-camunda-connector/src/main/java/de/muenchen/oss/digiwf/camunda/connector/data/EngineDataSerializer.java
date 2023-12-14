package de.muenchen.oss.digiwf.camunda.connector.data;

import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.community.rest.client.model.VariableValueDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class EngineDataSerializer {

    public Map<String, VariableValueDto> toEngineData(final Map<String, Object> data) {
        final JSONObject jsonData = new JSONObject(data);
        final Map<String, VariableValueDto> variables = new HashMap<>();
        jsonData.keySet().forEach(key -> {
                    final Object value = jsonData.get(key);
                    variables.put(key, this.toEngineData(value));
                }
        );
        return variables;
    }

    public Map<String, Object> fromEngineData(final VariableMap variables) {
        final Map<String, Object> data = new HashMap<>();

        variables.keySet().forEach(key -> {
            final TypedValue value = variables.getValueTyped(key);
            if (value.getType().getName().equals("json")) {
                data.put(key, this.fromEngineData(value.getValue()));
            } else {
                data.put(key, value.getValue());
            }
        });
        return data;
    }

    public VariableValueDto toEngineData(final Object value) {
        final VariableValueDto variableValueDto = new VariableValueDto();
        if (value instanceof JSONObject || value instanceof JSONArray) {
            variableValueDto.setValue(value.toString());
            variableValueDto.setType("json");
        } else {
            variableValueDto.setValue(value);
        }
        return variableValueDto;
    }

    //---------------------------------- helper methods ----------------------------------//

    private Object fromEngineData(final Object value) {
        if (value.toString().startsWith("[")) {
            return new JSONArray(value.toString()).toList();
        }
        return new JSONObject(value.toString()).toMap();
    }
}
