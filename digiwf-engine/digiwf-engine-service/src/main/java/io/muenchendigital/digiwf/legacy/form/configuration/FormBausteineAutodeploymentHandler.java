/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.configuration;

import io.muenchendigital.digiwf.legacy.form.api.mapper.FormTOMapper;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Autodeployment for bausteine forms.
 *
 * @author externer.dl.horn
 */
@Component
@ConditionalOnProperty("digiwf.form.autodeployBausteine")
public class FormBausteineAutodeploymentHandler extends FormAutodeploymentHandler {

    public FormBausteineAutodeploymentHandler(final ResourceLoader resourceLoader, final FormTOMapper formMapper, final FormService formService) {
        super(resourceLoader, formMapper, formService);
    }

    @Override
    public Resource[] getResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources("classpath:bausteine/**/*.form.json");
    }
}
