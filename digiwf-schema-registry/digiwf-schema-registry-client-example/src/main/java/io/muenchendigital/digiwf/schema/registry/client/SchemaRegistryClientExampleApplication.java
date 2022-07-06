package io.muenchendigital.digiwf.schema.registry.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SchemaRegistryClientExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SchemaRegistryClientExampleApplication.class, args);
    }

}
