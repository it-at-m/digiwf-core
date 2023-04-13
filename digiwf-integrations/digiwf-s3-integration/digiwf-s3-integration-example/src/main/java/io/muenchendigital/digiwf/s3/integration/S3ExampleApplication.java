package io.muenchendigital.digiwf.s3.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class S3ExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(S3ExampleApplication.class, args);
    }

}
