package de.muenchen.oss.digiwf.deployment.receiver.handler;


import de.muenchen.oss.digiwf.process.config.domain.model.ConfigEntry;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
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
class ConfigurationDeploymentHandlerTest {

    private final ProcessConfigService processConfigService = mock(ProcessConfigService.class);

    private final ConfigurationDeploymentHandler handler = new ConfigurationDeploymentHandler(processConfigService);

    private final String config = "{\n" +
            "  \"key\": \"key\",\n" +
            "  \"statusConfig\": [],\n" +
            "  \"configs\": [\n" +
            "    {\n" +
            "      \"key\": \"app_file_paths_readonly\",\n" +
            "      \"value\": \"test\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"app_file_paths\",\n" +
            "      \"value\": \"test\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"app_instance_schema_key\",\n" +
            "      \"value\": \"key\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    void test_DeployIsResponsibleForBpmn() {
        assertThat(handler.isResponsibleFor("config")).isTrue();
        assertThat(handler.isResponsibleFor("somethingElse")).isFalse();
    }

    @Test
    void test_DeployArtifact() {
        final ProcessConfig processConfig = ProcessConfig.builder()
                .key("key")
                .configs(List.of(
                        ConfigEntry.builder().key("app_file_paths_readonly").value("test").build(),
                        ConfigEntry.builder().key("app_file_paths").value("test").build(),
                        ConfigEntry.builder().key("app_instance_schema_key").value("key").build(),
                        ConfigEntry.builder().key("ignore_fields_on_start").value("1").build()
                ))
                .build();
        when(processConfigService.saveProcessConfig(any(ProcessConfig.class))).thenReturn(processConfig);

        final Deployment deployment = new Deployment(config, "config", "filename", "namespace", List.of("LATEST"));
        this.handler.deployArtifact(deployment);

        final ArgumentCaptor<ProcessConfig> processConfigCaptor = ArgumentCaptor.forClass(ProcessConfig.class);

        verify(this.processConfigService, times(1)).saveProcessConfig(processConfigCaptor.capture());
        assertThat(processConfigCaptor.getValue().getKey()).isEqualTo("key");
        assertThat(processConfigCaptor.getValue().getConfigs())
                .hasSize(3)
                .contains(ConfigEntry.builder().key("app_file_paths_readonly").value("test").build())
                .contains(ConfigEntry.builder().key("app_file_paths").value("test").build())
                .contains(ConfigEntry.builder().key("app_instance_schema_key").value("key").build());
        assertThat(processConfigCaptor.getValue().getStatusConfig()).isEmpty();
    }

    @Test
    void test_DeployArtifactThrowsDeploymentFailedExceptionIfConfigIsInvalid() {
        final Deployment deployment = new Deployment("invalid-config", "config", "filename", "namespace", List.of("LATEST"));
        assertThatThrownBy(() -> handler.deployArtifact(deployment))
                .isInstanceOf(DeploymentFailedException.class);
    }

}
