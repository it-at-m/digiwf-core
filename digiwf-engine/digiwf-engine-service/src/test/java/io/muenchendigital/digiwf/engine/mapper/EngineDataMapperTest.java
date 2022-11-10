package io.muenchendigital.digiwf.engine.mapper;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.plugin.variable.SpinValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@Import({EngineDataMapperImpl.class})
public class EngineDataMapperTest {


    @Autowired
    private EngineDataMapper engineDataMapper;


    @Test
    public void mapSimpleObjectDataToVariables() {
        final Map<String, Object> data = Map.of("test", "1");
        final Map<String, Object> result = this.engineDataMapper.mapObjectsToVariables(data);

        final Map<String, Object> variables = Variables.createVariables();

        variables.put("test", "1");

        assertEquals(variables.toString(), result.toString());
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

        assertEquals(variables.toString(), result.toString());
    }

}
