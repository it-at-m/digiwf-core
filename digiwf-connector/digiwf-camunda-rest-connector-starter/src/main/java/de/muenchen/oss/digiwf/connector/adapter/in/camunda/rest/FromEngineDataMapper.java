package de.muenchen.oss.digiwf.connector.adapter.in.camunda.rest;

import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class FromEngineDataMapper {

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

    //---------------------------------- helper methods ----------------------------------//

    private Object fromEngineData(final Object value) {
        if (value.toString().startsWith("[")) {
            return new JSONArray(value.toString()).toList();
        }
        return new JSONObject(value.toString()).toMap();
    }
}
