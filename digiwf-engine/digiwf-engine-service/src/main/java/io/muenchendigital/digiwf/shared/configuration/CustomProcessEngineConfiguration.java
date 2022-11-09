/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.configuration;

import io.muenchendigital.digiwf.engine.incidents.IncidentNotifierHandler;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Custom configuration for camunda process engine.
 *
 * @author martin.dietrich
 */
@Component
@Order(Ordering.DEFAULT_ORDER + 1)
public class CustomProcessEngineConfiguration implements ProcessEnginePlugin {

    @Autowired
    private IncidentNotifierHandler incidentNotifierHandler;

    @Override
    public void preInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setCustomIncidentHandlers(Arrays.asList(this.incidentNotifierHandler));
    }

    @Override
    public void postInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(final ProcessEngine processEngine) {

    }
}
