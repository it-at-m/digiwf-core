package io.muenchendigital.digiwf.service.definition.domain.facade;

import io.muenchendigital.digiwf.jsonschema.domain.model.JsonSchema;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import io.muenchendigital.digiwf.service.config.process.ProcessConfigFunctions;
import io.muenchendigital.digiwf.service.definition.domain.model.ServiceDefinition;
import io.muenchendigital.digiwf.service.definition.domain.model.ServiceDefinitionDetail;
import io.muenchendigital.digiwf.service.definition.domain.model.StartContext;
import io.muenchendigital.digiwf.service.definition.domain.service.ServiceDefinitionAuthService;
import io.muenchendigital.digiwf.service.definition.domain.service.ServiceDefinitionDataService;
import io.muenchendigital.digiwf.service.definition.domain.service.ServiceDefinitionService;
import io.muenchendigital.digiwf.service.definition.domain.service.ServiceStartContextService;
import io.muenchendigital.digiwf.shared.exception.IllegalResourceAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.muenchendigital.digiwf.service.instance.process.ProcessConstants.PROCESS_S3_ASYNC_CONFIG;
import static io.muenchendigital.digiwf.service.instance.process.ProcessConstants.PROCESS_S3_SYNC_CONFIG;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceDefinitionFacade {

    private final ServiceDefinitionService serviceDefinitionService;
    private final ServiceDefinitionAuthService serviceDefinitionAuthService;
    private final ServiceDefinitionDataService serviceDefinitionDataService;
    private final ServiceStartContextService serviceStartContextService;

    private final JsonSchemaService jsonSchemaService;

    private final FormService formService;
    private final org.camunda.bpm.engine.FormService camundaFormService;
    private final ProcessConfigFunctions processConfigFunctions;

    public void startInstance(final String key, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, null, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, businessKey, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final String fileContext, final Map<String, Object> variables, final String userId, final List<String> groups) {
        final StartContext startContext = this.serviceStartContextService.getStartContext(userId, key).orElse(new StartContext(userId, key, fileContext));
        final ServiceDefinitionDetail definition = this.getServiceDefinitionDetailAuthorized(key, userId, groups);
        final Map<String, Object> serializedVariables;
        serializedVariables = this.serviceDefinitionDataService.serializeVariables(definition, variables);

        this.processConfigFunctions.get("app_file_s3_async_config", definition.getKey())
                .ifPresent(configValue -> serializedVariables.put(PROCESS_S3_ASYNC_CONFIG, configValue));
        this.processConfigFunctions.get("app_file_s3_sync_config", definition.getKey())
                .ifPresent(configValue -> serializedVariables.put(PROCESS_S3_SYNC_CONFIG, configValue));

        this.serviceDefinitionService.startInstance(definition, businessKey, serializedVariables, userId, startContext);
        this.serviceStartContextService.deleteStartContext(userId, key);
    }

    public ServiceDefinitionDetail getServiceDefinitionDetail(final String key, final String userId, final List<String> groups) {
        if (!this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, key)) {
            throw new IllegalResourceAccessException(String.format("Service definition not accessible: %s", key));
        }

        //TODO this could be in a separate call for initalizing a start form
        if (this.serviceStartContextService.getStartContext(userId, key).isEmpty()) {
            this.serviceStartContextService.createStartContext(userId, key);
        }

        final ServiceDefinitionDetail detail = this.serviceDefinitionService.getServiceDefinitionDetail(key, userId, groups);
        this.addFormData(detail);
        return detail;
    }


    public List<ServiceDefinition> getStartableServiceDefinitions(final String userId, final List<String> groups) {
        final List<ServiceDefinition> serviceDefinitions = this.serviceDefinitionService.getServiceDefinitions();
        return serviceDefinitions.stream()
                .filter(definition -> this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, definition.getKey()))
                .collect(Collectors.toList());
    }

    //--------------------------------------- Helper Methods ---------------------------------------//

    private void addFormData(final ServiceDefinitionDetail detail) {
        final String formKey = this.camundaFormService.getStartFormKey(detail.getId());

        final Optional<Form> form = this.formService.getStartForm(formKey);
        form.ifPresent(detail::setForm);

        if (form.isEmpty()) {
            final Optional<JsonSchema> schema = this.jsonSchemaService.getByKey(formKey);
            schema.map(JsonSchema::getSchemaMap).ifPresent(detail::setJsonSchema);
        }
    }

    private ServiceDefinitionDetail getServiceDefinitionDetailAuthorized(final String key, final String userId, final List<String> groups) {
        if (!this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, key)) {
            throw new IllegalResourceAccessException(String.format("Service definition not accessible: %s", key));
        }

        final ServiceDefinitionDetail detail = this.serviceDefinitionService.getServiceDefinitionDetail(key, userId, groups);
        this.addFormData(detail);
        return detail;
    }

}
