package de.muenchen.oss.digiwf.deployment.receiver.handler;


import de.muenchen.oss.digiwf.jsonschema.domain.service.JsonSchemaService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@Component
@RequiredArgsConstructor
class FormDeploymentHandlerTest {

    private final JsonSchemaService jsonSchemaService = mock(JsonSchemaService.class);

    private final FormDeploymentHandler handler = new FormDeploymentHandler(jsonSchemaService);

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("form")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

}
