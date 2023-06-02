package io.muenchendigital.digiwf.task.service.infra.engine;

import io.muenchendigital.digiwf.task.service.adapter.out.engine.LegacyTaskClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Feign for legacy task client endpoints.
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
@Configuration
@EnableFeignClients(clients = {LegacyTaskClient.class})
public class RemoteLegacyTaskClientConfiguration {

}
