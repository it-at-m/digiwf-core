/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.infrastructure;

import de.muenchen.oss.digiwf.alw.integration.adapter.out.orgstructure.OrgStructureMapperAdapter;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.OrgStructureMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;
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
    public OrgStructureMapper sachbearbeitungMapper() {
        if (sachbearbeitungMap.isEmpty()) {
            throw new IllegalArgumentException("alw-sachbearbeitung.properties is empty");
        }
        return new OrgStructureMapperAdapter(sachbearbeitungMap);
    }

}
