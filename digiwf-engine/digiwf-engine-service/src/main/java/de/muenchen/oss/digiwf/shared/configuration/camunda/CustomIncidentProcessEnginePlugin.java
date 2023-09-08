/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.shared.configuration.camunda;

import de.muenchen.oss.digiwf.engine.incidents.IncidentNotifierHandler;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Custom configuration for camunda process engine.
 *
 * @author martin.dietrich
 */
@Component
@Order(Ordering.DEFAULT_ORDER + 1)
@RequiredArgsConstructor
public class CustomIncidentProcessEnginePlugin implements ProcessEnginePlugin {

    private final IncidentNotifierHandler incidentNotifierHandler;

    @Override
    public void preInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setCustomIncidentHandlers(Collections.singletonList(this.incidentNotifierHandler));
    }

    @Override
    public void postInit(final ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(final ProcessEngine processEngine) {

    }
}
