/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.configuration;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.legacy.form.api.mapper.FormTOMapper;
import de.muenchen.oss.digiwf.legacy.form.api.transport.FormTO;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.form.domain.service.FormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Autodeployment for process forms.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty("digiwf.form.autodeploy")
public class FormAutodeploymentHandler {

    protected final ResourceLoader resourceLoader;
    private final FormTOMapper formMapper;
    private final FormService formService;

    @PostConstruct
    public void autoDeployForms() throws IOException {
        val resources = this.getResources();
        val forms = new ArrayList<Form>();
        for (final Resource resource : resources) {
            try {
                final Form map = this.map(resource);
                forms.add(map);
            } catch (final Exception error) {
                log.error("Could not load form: {}", resource.getFilename(), error);
            }
        }

        for (val form : forms) {
            try {
                this.formService.saveForm(form);
            } catch (final Exception error) {
                log.error("Could not deploy form: {}", form.getKey(), error);
            }
        }
    }

    public Resource[] getResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources("classpath:prozesse/**/*.form.json");
    }

    private Form map(final Resource resource) throws IOException {
        val formJson = this.asString(resource);
        val to = new Gson().fromJson(formJson, FormTO.class);
        return this.formMapper.map(to);
    }

    private String asString(final Resource resource) throws IOException {
        try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

}
