package io.muenchendigital.digiwf.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class DigiWFEngineApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DigiWFEngineApplication.class, args);
    }

}
