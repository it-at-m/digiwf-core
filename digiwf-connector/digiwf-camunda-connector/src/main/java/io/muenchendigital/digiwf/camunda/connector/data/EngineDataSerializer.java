package io.muenchendigital.digiwf.camunda.connector.data;

import org.camunda.community.rest.client.dto.VariableValueDto;
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

    public Map<String, Object> fromEngineData(final Map<String, Object> variables) {
        final Map<String, Object> data = new HashMap<>();
        variables.forEach((key, value) -> {
//            if (value instanceof JacksonJsonNode) {
//                data.put(key, this.fromEngineData((JacksonJsonNode) value));
//            } else {
            data.put(key, value);
//            }
        });
        return data;
    }

    //---------------------------------- helper methods ----------------------------------//

    private VariableValueDto toEngineData(final Object value) {
        final VariableValueDto variableValueDto = new VariableValueDto();
        if (value instanceof JSONObject || value instanceof JSONArray) {
            variableValueDto.setValue(value.toString());
            variableValueDto.setType("json");
        } else {
            variableValueDto.setValue(value);
        }
        return variableValueDto;
    }
//
//    private Object fromEngineData(final JacksonJsonNode object) {
//        if (object.isArray()) {
//            return new JSONArray(object.toString()).toList();
//        }
//        return new JSONObject(object.toString()).toMap();
//    }


}
