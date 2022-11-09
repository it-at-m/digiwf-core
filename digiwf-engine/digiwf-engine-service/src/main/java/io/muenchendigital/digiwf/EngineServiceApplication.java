/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
@EnableScheduling
public class EngineServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EngineServiceApplication.class, args);
    }

}
