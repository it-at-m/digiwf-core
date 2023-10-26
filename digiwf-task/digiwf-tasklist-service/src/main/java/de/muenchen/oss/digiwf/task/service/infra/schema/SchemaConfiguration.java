package de.muenchen.oss.digiwf.task.service.infra.schema;

import de.muenchen.oss.digiwf.task.service.adapter.out.schema.JsonSchemaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = { JsonSchemaClient.class })
public class SchemaConfiguration {
}
