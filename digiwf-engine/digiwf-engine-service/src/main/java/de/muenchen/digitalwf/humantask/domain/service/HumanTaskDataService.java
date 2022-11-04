/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.humantask.domain.service;

import de.muenchen.digitalwf.engine.mapper.EngineDataMapper;
import de.muenchen.digitalwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.digitalwf.jsonschema.domain.service.JsonSchemaService;
import de.muenchen.digitalwf.legacy.form.domain.model.Form;
import de.muenchen.digitalwf.legacy.form.domain.service.FormService;
import de.muenchen.digitalwf.legacy.shared.data.DataService;
import de.muenchen.digitalwf.shared.exception.VariablesNotValidException;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.validation.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static de.muenchen.digitalwf.humantask.process.ProcessTaskConstants.*;
import static de.muenchen.digitalwf.service.instance.process.ProcessConstants.PROCESS_FILE_CONTEXT;

/**
 * Service to handle HumanTasks in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HumanTaskDataService {


    //outdated form handling
    private final DataService dataService;
    private final FormService formService;

    private final JsonSchemaService jsonSchemaService;
    private final JsonSerializationService serializationService;
    private final JsonSchemaValidator jsonSchemaValidator;

    //Camunda Services
    private final TaskService taskService;
    private final RuntimeService runtimeService;

    private final EngineDataMapper engineDataMapper;


    public Map<String, Object> serializeGivenVariables(final Task task, final Map<String, Object> variables) {
        val formKey = task.getFormKey();
        for (val variable : variables.entrySet()) {
            if (variable.getValue() == null) {
                variable.setValue("");
            }
        }

        final Optional<Form> form = this.formService.getForm(formKey);

        if (form.isPresent()) {
            final Map<String, Object> filteredVariables = this.dataService.filterReadonly(form.get(), variables);
            //2. are variables valid?
            if (!this.formService.validateVariables(formKey, filteredVariables)) {
                throw new VariablesNotValidException("Not allowed");
            }

            // add other serializer
            return this.dataService.serializeVariables(form.get(), filteredVariables);

        } else {
            final JsonSchema jsonSchema = this.jsonSchemaService.getByKey(this.getSchemaKey(task.getId()).orElseThrow())
                    .orElseThrow();
            return this.serializeData(jsonSchema, task, variables);
        }
    }


    public Map<String, Object> getVariablesForTask(final Task task) {
        final Optional<Form> form = this.formService.getForm(task.getFormKey());

        if (form.isPresent()) {
            final Map<String, Object> variables = this.taskService.getVariables(task.getId());
            return this.dataService.calculateDefaultValues(form.get(), variables);
        } else {
            final JsonSchema jsonSchema = this.jsonSchemaService.getByKey(this.getSchemaKey(task.getId()).orElseThrow())
                    .orElseThrow();
            return this.engineDataMapper.mapToData(this.serializationService.deserializeData(jsonSchema.getSchema(), this.taskService.getVariablesTyped(task.getId())));
        }
    }

    public String getTaskDescription(final String taskId) {
        Object variable = this.taskService.getVariable(taskId, TASK_DESCRIPTION);
        if (variable == null) {
            variable = this.taskService.getVariable(taskId, TASK_DESCRIPTION_DIGITALWF);
        }
        if (variable == null) {
            return "";
        }
        return variable.toString();
    }

    public Optional<String> getSchemaKey(final String taskId) {
        return Optional.ofNullable(this.taskService.getVariable(taskId, TASK_SCHEMA_KEY))
                .map(Object::toString);
    }

    public Optional<String> getVariable(final String taskId, final String key) {
        return Optional.ofNullable(this.taskService.getVariable(taskId, key))
                .map(Object::toString);
    }

    public Optional<String> getFileContext(final String taskId) {
        return Optional.ofNullable(this.taskService.getVariable(taskId, PROCESS_FILE_CONTEXT))
                .map(Object::toString);
    }

    //----------------------------------------- HELPER METHODS -----------------------------------------//

    private Map<String, Object> serializeData(final JsonSchema schema, final Task task, final Map<String, Object> variables) {

        final JSONObject filteredData = this.serializationService.filter(schema.getSchema(), variables, true);
        this.jsonSchemaValidator.validate(schema.getSchema(), filteredData.toMap());
        final Map<String, Object> taskData = this.mapTaskData(task);
        final Map<String, Object> targetData = this.serializationService.deserializeData(schema.getSchema(), taskData);
        final Map<String, Object> serializedData = this.serializationService.merge(filteredData, new JSONObject(targetData));
        final JSONObject defaultValue = this.serializationService.initialize(new JSONObject(schema.getSchema()).toString());
        final Map<String, Object> serializedDataWithDefaultValues = this.serializationService.merge(new JSONObject(serializedData), defaultValue);
        return this.engineDataMapper.mapObjectsToVariables(serializedDataWithDefaultValues);
    }

    private Map<String, Object> mapTaskData(final Task task) {
        return this.engineDataMapper.mapToData(this.taskService.getVariables(task.getId()));
    }

}
