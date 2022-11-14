/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.alw.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
public class MicroServiceApplication {


    public static void main(final String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }


}
