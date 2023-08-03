/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * Config bean to provide a resource map for mapping sachbearbeitung.
 */
@Configuration
@Slf4j
public class SachbearbeitungMapperResourceConfig {

    /**
     * Location of the property file.
     */
    @Value("${digiwf.alw.personeninfo.sachbearbeitung-mapping-config-url}")
    private String sachbearbeitungMappingConfigUrl;

    @Bean(name = SachbearbeitungMapperConfig.BEAN_ALW_SACHBEARBEITUNG)
    public PropertiesFactoryBean alwSachbearbeitung() {
        log.debug("sachbearbeitungMappingConfigUrl: {}", sachbearbeitungMappingConfigUrl);
        final PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(
                sachbearbeitungMappingConfigUrl));
        return bean;
    }

}
