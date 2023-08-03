/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.domain.service;

import de.muenchen.oss.digiwf.process.config.domain.mapper.ProcessConfigMapper;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.infrastructure.repository.ProcessConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to interact with the {@link ProcessConfig}
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigMapper processConfigConvert;
    private final ProcessConfigRepository processConfigJpaRepository;

    /**
     * Get process config by key.
     *
     * @param key key of the process config
     * @return process config
     */
    public Optional<ProcessConfig> getProcessConfig(final String key) {
        return this.processConfigJpaRepository.findByKey(key)
                .map(this.processConfigConvert::map);
    }

    /**
     * Save a process config.
     *
     * @param processConfig process config that is saved
     * @return process config
     */
    public ProcessConfig saveProcessConfig(final ProcessConfig processConfig) {
        val entity = this.processConfigJpaRepository.findByKey(processConfig.getKey());
        val newEntity = this.processConfigConvert.map2Entity(processConfig);
        entity.ifPresent(formEntity -> newEntity.setId(formEntity.getId()));
        val savedEntity = this.processConfigJpaRepository.save(newEntity);
        log.info("process config deployed: {}", savedEntity);

        return this.processConfigConvert.map(savedEntity);
    }
}
