/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.adapter.in.rest.EngineRestGroupFilter;
import de.muenchen.oss.digiwf.adapter.in.rest.RestMapper;
import de.muenchen.oss.digiwf.application.port.in.ResolveUserGroupsInPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class EngineRestServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EngineRestServiceApplication.class, args);
    }


    /**
     * Registriert den Filter für die Camunda-Group Abfrage.
     */
    @Bean
    public FilterRegistrationBean<EngineRestGroupFilter> engineRestGroupFilter(
        ObjectMapper objectMapper,
        ResolveUserGroupsInPort resolveUserGroupsInPort,
        RestMapper restMapper
    ) {
        FilterRegistrationBean<EngineRestGroupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new EngineRestGroupFilter(objectMapper, resolveUserGroupsInPort, restMapper));
        filterRegistrationBean.setOrder(101);
        filterRegistrationBean.addUrlPatterns("/engine-rest/engine/default/group");
        return filterRegistrationBean;
    }

}
