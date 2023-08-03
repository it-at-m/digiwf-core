/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import de.muenchen.oss.digiwf.alw.integration.domain.mapper.SachbearbeitungMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Config for mapping sachbearbeitung.
 */
@Configuration
public class SachbearbeitungMapperConfig {

    /**
     * Name of bean with config properties for alw-sachbearbeitung.
     */
    public static final String BEAN_ALW_SACHBEARBEITUNG = "alwSachbearbeitung";

    @Resource(name = SachbearbeitungMapperConfig.BEAN_ALW_SACHBEARBEITUNG)
    private Map<String, String> sachbearbeitungMap;

    @Bean
    public SachbearbeitungMapper sachbearbeitungMapper() {
        return new SachbearbeitungMapper(sachbearbeitungMap);
    }

}
