/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.shared.configuration;

import de.muenchen.oss.digiwf.shared.gracefulshutdown.GracefulShutdown;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The config class to register the {@link GracefulShutdown} logic within the webserver
 * to enable a graceful shutdown.
 */
@Configuration
@ComponentScan(basePackages = {"de.muenchen.allgemein.gracefulshutdown"})
@ConditionalOnProperty(name = "tomcat.gracefulshutdown.enabled", havingValue = "true", matchIfMissing = true)
public class GracefulShutdownConfiguration {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
        final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }

}
