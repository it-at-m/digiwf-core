/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.configuration;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Config bean to provide a resource map for mapping sachbearbeitung.
 */
@Configuration
public class SachbearbeitungMapperResourceConfig {

    /**
     * Location of the property file.
     */
    public static final String ALW_SACHBEARBEITUNG_PROPERTYFILE = "/config/alw-sachbearbeitung.properties";

    @Bean(name = SachbearbeitungMapperConfig.BEAN_ALW_SACHBEARBEITUNG)
    public static PropertiesFactoryBean alwSachbearbeitungLocal() {
        final PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource(
                ALW_SACHBEARBEITUNG_PROPERTYFILE));
        return bean;
    }

}
