/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.config.deployment;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.service.config.api.mapper.ProcessConfigApiMapper;
import io.muenchendigital.digiwf.service.config.api.transport.ProcessConfigTO;
import io.muenchendigital.digiwf.service.config.domain.model.ProcessConfig;
import io.muenchendigital.digiwf.service.config.domain.service.ProcessConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Auto deployment of process configurations.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty("digiwf.config.autodeploy")
public class ProcessConfigAutodeploymentHandler {

    private final ResourceLoader resourceLoader;
    private final ProcessConfigApiMapper processConfigMapper;
    private final ProcessConfigService processConfigService;

    @PostConstruct
    public void autoDeployForms() throws IOException {
        val resources = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources("classpath:prozesse/**/*.processconfig.json");
        val configs = new ArrayList<ProcessConfig>();
        for (final Resource resource : resources) {
            try {
                val map = this.map(resource);
                configs.add(map);
            } catch (final Exception error) {
                log.error("The config could no be loaded: {}", resource.getFilename(), error);
            }
        }

        for (val config : configs) {
            try {
                this.processConfigService.saveProcessConfig(config);
            } catch (final Exception error) {
                log.error("The config could no be loaded: {}", config.getKey(), error);
            }
        }
    }

    private ProcessConfig map(final Resource resource) throws IOException {
        val json = this.asString(resource);
        val to = new Gson().fromJson(json, ProcessConfigTO.class);
        return this.processConfigMapper.map(to);
    }

    private String asString(final Resource resource) throws IOException {
        try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

}
