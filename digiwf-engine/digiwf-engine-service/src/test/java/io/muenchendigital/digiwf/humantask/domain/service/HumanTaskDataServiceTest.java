package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.engine.mapper.EngineDataMapper;
import io.muenchendigital.digiwf.humantask.process.ProcessTaskConstants;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.validation.JsonSchemaValidator;
import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import io.muenchendigital.digiwf.legacy.shared.data.DataService;
import io.muenchendigital.digiwf.testutils.Helper;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class HumanTaskDataServiceTest {

    @InjectMocks
    private HumanTaskDataService testObject;

    @Mock
    private DataService dataService;
    @Mock
    private FormService formService;
    @Mock
    private JsonSchemaService jsonSchemaService;
    @Mock
    private JsonSerializationService serializationService;
    @Mock
    private JsonSchemaValidator jsonSchemaValidator;
    @Mock
    private TaskService taskService;
    @Mock
    private EngineDataMapper engineDataMapper;


    /**
     * Missing Form should result in way to complex data serialization
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testSerializeGivenVariablesWithMissingForm() throws IOException, URISyntaxException {
        String taskId = "task-id-1";
        Task task = Mockito.mock(Task.class);
        String schemaKey = "schema-key-1";
        String schema = Helper.getJsonObject("/schema/basic/simpleSchema.json").toString();
        JsonSchema jsonSchema = JsonSchema.builder().key(schemaKey).schema(schema).build();
        JSONObject data = Helper.getJsonObject("/schema/basic/simpleData.json");

        Mockito.when(task.getId()).thenReturn(taskId);
        Mockito.when(this.formService.getForm(any())).thenReturn(Optional.empty());
        Mockito.when(this.jsonSchemaService.getByKey(schemaKey)).thenReturn(Optional.of(jsonSchema));
        Mockito.when(this.taskService.getVariable(taskId, ProcessTaskConstants.TASK_SCHEMA_KEY)).thenReturn(schemaKey);
        Mockito.when(this.serializationService.filter(jsonSchema.getSchemaMap(), data.toMap(), true)).thenReturn(data);
        Mockito.when(this.taskService.getVariables(taskId)).thenReturn(Collections.emptyMap());
        Mockito.when(this.engineDataMapper.mapToData(Collections.emptyMap())).thenReturn(data.toMap());
        Mockito.when(this.serializationService.deserializeData(jsonSchema.getSchemaMap(), data.toMap())).thenReturn(data.toMap());
        Mockito.when(this.serializationService.merge(any(), any())).thenReturn(data.toMap());
        Mockito.when(this.serializationService.initialize(schema)).thenReturn(data);
        Mockito.when(this.engineDataMapper.mapObjectsToVariables(data.toMap())).thenReturn(data.toMap());

        var result = this.testObject.serializeGivenVariables(task, data.toMap());

        assertThat(result).isEqualTo(data.toMap());

        Mockito.verify(this.formService, Mockito.times(1)).getForm(any());
        Mockito.verify(this.jsonSchemaService, Mockito.times(1)).getByKey(schemaKey);
        Mockito.verify(this.taskService, Mockito.times(1)).getVariable(taskId, ProcessTaskConstants.TASK_SCHEMA_KEY);
        Mockito.verify(this.serializationService, Mockito.times(1)).filter(jsonSchema.getSchemaMap(), data.toMap(), true);
        Mockito.verify(this.taskService, Mockito.times(1)).getVariables(taskId);
        Mockito.verify(this.engineDataMapper, Mockito.times(1)).mapToData(Collections.emptyMap());
        Mockito.verify(this.serializationService, Mockito.times(1)).deserializeData(jsonSchema.getSchemaMap(), data.toMap());
        Mockito.verify(this.serializationService, Mockito.times(2)).merge(any(), any());
        Mockito.verify(this.serializationService, Mockito.times(1)).initialize(schema);
        Mockito.verify(this.engineDataMapper, Mockito.times(1)).mapObjectsToVariables(data.toMap());

    }
}
