package de.muenchen.oss.digiwf.process.definition.domain.facade;

import de.muenchen.oss.digiwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.oss.digiwf.jsonschema.domain.service.JsonSchemaService;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.form.domain.service.FormService;
import de.muenchen.oss.digiwf.process.config.process.ProcessConfigFunctions;
import de.muenchen.oss.digiwf.process.definition.domain.mapper.ServiceDefinitionPageMapper;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinitionDetail;
import de.muenchen.oss.digiwf.process.definition.domain.model.StartContext;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionAuthService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionDataService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceStartContextService;
import de.muenchen.oss.digiwf.process.instance.process.properties.S3Properties;
import de.muenchen.oss.digiwf.shared.exception.IllegalResourceAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.muenchen.oss.digiwf.process.instance.process.ProcessConstants.PROCESS_S3_ASYNC_CONFIG;
import static de.muenchen.oss.digiwf.process.instance.process.ProcessConstants.PROCESS_S3_SYNC_CONFIG;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceDefinitionFacade {

    private final ServiceDefinitionService serviceDefinitionService;
    private final ServiceDefinitionAuthService serviceDefinitionAuthService;
    private final ServiceDefinitionDataService serviceDefinitionDataService;
    private final ServiceStartContextService serviceStartContextService;
    private final ServiceDefinitionPageMapper serviceDefinitionPageMapper;

    private final JsonSchemaService jsonSchemaService;

    private final FormService formService;
    private final org.camunda.bpm.engine.FormService camundaFormService;
    private final ProcessConfigFunctions processConfigFunctions;
    private final S3Properties s3Properties;

    public void startInstance(final String key, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, null, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final Map<String, Object> variables, final String userId, final List<String> groups) {
        this.startInstance(key, businessKey, null, variables, userId, groups);
    }

    public void startInstance(final String key, final String businessKey, final String fileContext, final Map<String, Object> variables, final String userId, final List<String> groups) {
        final StartContext startContext = this.serviceStartContextService.getStartContext(userId, key).orElse(new StartContext(userId, key, fileContext));
        final ServiceDefinitionDetail definition = this.getServiceDefinitionDetailAuthorized(key, userId, groups);
        final Map<String, Object> serializedVariables = this.serviceDefinitionDataService.serializeVariables(definition, variables);

        // get the s3-integrations topic or use the default one from application properties
        // and set it as a variable on process start
        this.processConfigFunctions.get("app_file_s3_async_config", definition.getKey())
                .ifPresentOrElse(
                        configValue -> serializedVariables.put(PROCESS_S3_ASYNC_CONFIG, configValue),
                        () -> serializedVariables.put(PROCESS_S3_ASYNC_CONFIG, this.s3Properties.getTopic())
                );
        this.processConfigFunctions.get("app_file_s3_sync_config", definition.getKey())
                .ifPresentOrElse(
                        configValue -> serializedVariables.put(PROCESS_S3_SYNC_CONFIG, configValue),
                        () -> serializedVariables.put(PROCESS_S3_SYNC_CONFIG, this.s3Properties.getHttpAPI())
                );

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


    public Page<ServiceDefinition> getStartableServiceDefinitions(
            final String userId,
            final List<String> groups,
            final int page,
            final int size,
            @Nullable
            final String query
            ) {
        final List<ServiceDefinition> serviceDefinitions = this.serviceDefinitionService.getServiceDefinitions().stream()
                .filter(definition -> this.serviceDefinitionAuthService.allowedToStartDefinition(userId, groups, definition.getKey()))
                .collect(Collectors.toList());
        return serviceDefinitionPageMapper.toPage(serviceDefinitions, page, size, query);
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
