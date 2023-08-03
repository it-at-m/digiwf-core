package de.muenchen.oss.digiwf.schema.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class DigiWFSchemaRegistryApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DigiWFSchemaRegistryApplication.class, args);
    }

}

