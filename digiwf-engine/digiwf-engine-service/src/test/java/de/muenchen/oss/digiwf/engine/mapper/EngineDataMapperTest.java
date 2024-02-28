package de.muenchen.oss.digiwf.engine.mapper;

import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.plugin.variable.SpinValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EngineDataMapperTest {

    private final EngineDataMapper engineDataMapper = new EngineDataMapperImpl();

    @Test
    public void mapSimpleObjectDataToVariables() {
        final Map<String, Object> data = Map.of("test", "1");
        final Map<String, Object> result = this.engineDataMapper.mapObjectsToVariables(data);

        final Map<String, Object> variables = Variables.createVariables();

        variables.put("test", "1");

        assertThat(variables.toString()).isEqualTo(result.toString());
    }

    @Test
    public void mapComplexObjectDataToVariables() {
        final Map<String, Object> data = Map.of(
                "object", Map.of("key", "2"),
                "array", List.of("1", "2", "3")
        );

        final Map<String, Object> result = this.engineDataMapper.mapObjectsToVariables(data);

        final Map<String, Object> variables = Variables.createVariables();
        variables.put("object", SpinValues.jsonValue(new JSONObject(Map.of("key", "2")).toString()).create());
        variables.put("array", SpinValues.jsonValue(new JSONArray(List.of("1", "2", "3")).toString()).create());

        assertThat(variables.toString()).isEqualTo(result.toString());
    }
}
