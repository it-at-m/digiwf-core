package io.muenchendigital.digiwf.task.service.infra.schema;

import io.muenchendigital.digiwf.task.service.adapter.out.schema.JsonSchemaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = { JsonSchemaClient.class })
public class SchemaConfiguration {
}
