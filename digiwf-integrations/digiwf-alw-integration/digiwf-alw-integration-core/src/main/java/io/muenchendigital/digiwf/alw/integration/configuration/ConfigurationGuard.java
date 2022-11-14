/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.configuration;

import io.muenchendigital.digiwf.alw.integration.domain.mapper.SachbearbeitungMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validates the sachbearbeiter mapping configuration.
 */
@Component
@Slf4j
public class ConfigurationGuard implements InitializingBean {

    @Autowired
    private SachbearbeitungMapper mapper;

    @Override
    public void afterPropertiesSet() {
        if (!mapper.isInitialized()) {
            final String message = "alw-sachbearbeitung.properties is empty";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

}