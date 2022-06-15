package io.muenchendigital.digiwf.engine.data;

import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.impl.json.jackson.JacksonJsonNode;
import org.camunda.spin.plugin.variable.SpinValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class EngineDataSerializer {

    public Map<String, Object> toEngineData(final Map<String, Object> data) {
        final JSONObject jsonData = new JSONObject(data);
        final Map<String, Object> variables = Variables.createVariables();
        jsonData.keySet().forEach(key -> {
                    final Object value = jsonData.get(key);
                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        variables.put(key, this.toEngineData(value));
                    } else {
                        variables.put(key, value);
                    }
                }
        );
        return variables;
    }

    public Map<String, Object> fromEngineData(final Map<String, Object> variables) {
        final Map<String, Object> data = Variables.createVariables();
        variables.forEach((key, value) -> {
            if (value instanceof JacksonJsonNode) {
                data.put(key, this.fromEngineData((JacksonJsonNode) value));
            } else {
                data.put(key, value);
            }
        });
        return data;
    }

    //---------------------------------- helper methods ----------------------------------//

    private Object toEngineData(final Object object) {
        return SpinValues.jsonValue(object.toString()).create();
    }

    private Object fromEngineData(final JacksonJsonNode object) {
        if (object.isArray()) {
            return new JSONArray(object.toString()).toList();
        }
        return new JSONObject(object.toString()).toMap();
    }


}
