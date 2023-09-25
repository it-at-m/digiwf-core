/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.configuration;

import de.muenchen.oss.digiwf.alw.integration.infrastructure.SachbearbeitungMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Config bean to provide a resource map for mapping sachbearbeitung.
 */
@Configuration
@Slf4j
public class SachbearbeitungMapperResourceConfig {

  private static final String CLASS_PATH = "classpath:";

  /**
   * Location of the property file.
   */
  @Value("${io.muenchendigital.digiwf.alw.personeninfo.sachbearbeitung-mapping-config-url}")
  private String sachbearbeitungMappingConfigUrl;

  @Bean(name = SachbearbeitungMapperConfig.BEAN_ALW_SACHBEARBEITUNG)
  public PropertiesFactoryBean alwSachbearbeitung() {
    final PropertiesFactoryBean bean = new PropertiesFactoryBean();
    log.info("Using Sachbearbeitung Mapping from {}", sachbearbeitungMappingConfigUrl);
    final Resource resource;
    if (sachbearbeitungMappingConfigUrl.startsWith(CLASS_PATH)) {
      resource = new ClassPathResource(sachbearbeitungMappingConfigUrl.substring(CLASS_PATH.length()));
    } else {
      resource = new FileSystemResource(sachbearbeitungMappingConfigUrl);
    }
    bean.setLocation(resource);
    return bean;
  }

}
