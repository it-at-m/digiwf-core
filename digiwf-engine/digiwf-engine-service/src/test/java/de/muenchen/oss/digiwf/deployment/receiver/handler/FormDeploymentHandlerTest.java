package de.muenchen.oss.digiwf.deployment.receiver.handler;


import de.muenchen.oss.digiwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.oss.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.miragon.miranum.deploymentreceiver.application.DeploymentFailedException;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Component
@RequiredArgsConstructor
class FormDeploymentHandlerTest {

    private final JsonSchemaService jsonSchemaService = mock(JsonSchemaService.class);

    private final FormDeploymentHandler handler = new FormDeploymentHandler(jsonSchemaService);

    private final String form = "{\n" +
            "    \"key\": \"Form_pq9pzs\",\n" +
            "    \"schema\": {\n" +
            "        \"key\": \"MyStartForm\",\n" +
            "        \"type\": \"object\",\n" +
            "        \"x-display\": \"stepper\",\n" +
            "        \"allOf\": [\n" +
            "            {\n" +
            "                \"key\": \"sectionKey1\",\n" +
            "                \"title\": \"First Section\",\n" +
            "                \"type\": \"object\",\n" +
            "                \"x-options\": {\n" +
            "                    \"sectionsTitlesClasses\": [\n" +
            "                        \"d-none\"\n" +
            "                    ]\n" +
            "                },\n" +
            "                \"allOf\": [\n" +
            "                    {\n" +
            "                        \"containerType\": \"group\",\n" +
            "                        \"title\": \"Group\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"x-options\": {\n" +
            "                            \"childrenClass\": \"pl-0\"\n" +
            "                        },\n" +
            "                        \"properties\": {\n" +
            "                            \"64f8be41-7dfa-4702-a181-db595fe2b335\": {\n" +
            "                                \"x-options\": {\n" +
            "                                    \"fieldColProps\": {\n" +
            "                                        \"cols\": 12,\n" +
            "                                        \"sm\": 12\n" +
            "                                    }\n" +
            "                                },\n" +
            "                                \"x-props\": {\n" +
            "                                    \"outlined\": true,\n" +
            "                                    \"dense\": true\n" +
            "                                },\n" +
            "                                \"fieldType\": \"text\",\n" +
            "                                \"title\": \"Textfield\",\n" +
            "                                \"type\": \"string\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"key\": \"b1050d5c-58c0-4990-b1b4-c0e37b8b2d0d\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("form")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

    @Test
    void test_DeployArtifact() {
        final JsonSchema jsonSchema = JsonSchema.builder()
                .key("Form_pq9pzs")
                .schema("some-schema")
                .build();
        when(jsonSchemaService.createJsonSchema(any(JsonSchema.class))).thenReturn(jsonSchema);

        final Deployment deployment = new Deployment(form, "form", "filename", "namespace", List.of("LATEST"));
        handler.deployArtifact(deployment);

        final ArgumentCaptor<JsonSchema> jsonSchemaCaptor = ArgumentCaptor.forClass(JsonSchema.class);
        verify(this.jsonSchemaService, times(1)).createJsonSchema(jsonSchemaCaptor.capture());
        assertThat(jsonSchemaCaptor.getValue().getKey()).isEqualTo("Form_pq9pzs");
        assertThat(jsonSchemaCaptor.getValue().getSchema()).isNotBlank();
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfFormIsInvalid() {
        final Deployment deployment = new Deployment("invalid-form", "form", "filename", "namespace", List.of("LATEST"));
        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class);
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfSchemaIsMissingInForm() {
        final Deployment deployment = new Deployment("{\"schema\": \"some-schema\"}", "form", "filename", "namespace", List.of("LATEST"));
        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class)
                .hasMessage("Model validation failed with message: Form key is missing");
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfKeyIsMissingInForm() {
        final Deployment deployment = new Deployment("{\"key\": \"key\"}", "form", "filename", "namespace", List.of("LATEST"));
        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class)
                .hasMessageContaining("Model validation failed with message: Form schema is missing");
    }

}
