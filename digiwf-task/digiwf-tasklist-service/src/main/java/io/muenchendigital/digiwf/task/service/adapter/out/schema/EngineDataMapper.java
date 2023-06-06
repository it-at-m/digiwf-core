package io.muenchendigital.digiwf.task.service.adapter.out.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.plugin.variable.SpinValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.Mapper;

import java.util.Map;

// FIXME -> Hard copy of "io.muenchendigital.digiwf.engine.mapper.EngineDataMapper" from digiwf-engine-service
@Mapper(componentModel = "spring")
public interface EngineDataMapper {

  ObjectMapper objectMapper = new ObjectMapper();

  default Map<String, Object> mapObjectsToVariables(final Map<String, Object> data) {
    final JSONObject jsonData = new JSONObject(data);
    final Map<String, Object> variables = Variables.createVariables();
    jsonData.keySet().forEach(key -> {
          final Object value = jsonData.get(key);
          if (value instanceof JSONObject || value instanceof JSONArray) {
            variables.put(key, this.mapJsonToVariables(value));
          } else {
            variables.put(key, value);
          }
        }
    );
    return variables;
  }

  default Map<String, Object> mapToData(final Map<String, Object> variables) {
    final Map<String, Object> data = Variables.createVariables();
    variables.forEach((key, value) -> {
      if (value instanceof Map) {
        Map<?, ?> mValue = (Map<?, ?>) value;
        if (mValue.get("type").equals("json")) {
          data.put(key, mapToData(mValue.get("value").toString()));
        } else {
          data.put(key, value);
        }
      } else {
        data.put(key, value);
      }
    });
    return data;
  }

  default Object mapJsonToVariables(final Object object) {
    return SpinValues.jsonValue(object.toString()).create();
  }

  //TODO Is there a more elegant way?
  default Object mapToData(final String jsonString) {
    try {
      val jacksonNode = objectMapper.readTree(jsonString);
      if (jacksonNode.isArray()) {
        return new JSONArray(jacksonNode.toString()).toList();
      }
      return new JSONObject(jacksonNode.toString()).toMap();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
