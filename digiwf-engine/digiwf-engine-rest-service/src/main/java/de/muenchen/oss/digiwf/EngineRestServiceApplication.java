/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.adapter.in.rest.EngineRestGroupFilter;
import de.muenchen.oss.digiwf.adapter.out.ldap.LdapMockOutPort;
import de.muenchen.oss.digiwf.adapter.out.ldap.LdapOutAdapter;
import de.muenchen.oss.digiwf.adapter.out.ldap.LdapProperties;
import de.muenchen.oss.digiwf.application.port.in.ResolveUserGroupsInPort;
import de.muenchen.oss.digiwf.application.port.out.ResolveUserGroupsOutPort;
import de.muenchen.oss.digiwf.application.usecase.ResolveUserGroupsUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.ContextSource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
@Slf4j
public class EngineRestServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EngineRestServiceApplication.class, args);
    }

    // adapter in

    /**
     * Registriert den Filter für die Camunda-Group Abfrage.
     */
    @Bean
    @Profile({"groups-ldap", "groups-mock"})
    public FilterRegistrationBean<EngineRestGroupFilter> engineRestGroupFilter(
            final ObjectMapper objectMapper,
            final ResolveUserGroupsInPort resolveUserGroupsInPort
    ) {
        FilterRegistrationBean<EngineRestGroupFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new EngineRestGroupFilter(objectMapper, resolveUserGroupsInPort));
        filterRegistrationBean.setOrder(101);
        filterRegistrationBean.addUrlPatterns("/engine-rest/engine/default/group");
        return filterRegistrationBean;
    }

    // application
    @Bean
    @Profile({"groups-ldap", "groups-mock"})
    public ResolveUserGroupsInPort resolveUserGroupsInPort(final ResolveUserGroupsOutPort resolveUserGroupsOutPort) {
        return new ResolveUserGroupsUseCase(resolveUserGroupsOutPort);
    }


    // adapter out
    @Bean
    @Profile("groups-ldap")
    public LdapProperties ldapProperties() {
        return new LdapProperties();
    }

    @Bean
    @Profile("groups-ldap")
    public ResolveUserGroupsOutPort ldapOutPort(final ContextSource contextSource, final LdapProperties ldapProperties) {
        return new LdapOutAdapter(contextSource, ldapProperties);
    }

    @Bean
    @Profile("groups-mock")
    public ResolveUserGroupsOutPort ldapMockOutPort() {
        return new LdapMockOutPort();
    }
}
