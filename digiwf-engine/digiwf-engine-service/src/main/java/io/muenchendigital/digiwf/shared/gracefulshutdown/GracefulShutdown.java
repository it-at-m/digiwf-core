/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2019
 */
package io.muenchendigital.digiwf.shared.gracefulshutdown;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the graceful shutdown by serving all pending requests after
 * shutdown initialization via a SIGTERM signal.
 * <p>
 * To use this functionality, the spring boot actuator endpoint "/actuator/health"
 * has to be activated within the application which uses this functionality.
 */
@Component
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdown.class);

    private static final long MILLIS_PER_SECOND = 1000L;

    private volatile Connector connector;

    @Getter
    @Value("${tomcat.gracefulshutdown.pre-wait-seconds:20}")
    private int preWaitSeconds;

    @Getter
    @Value("${tomcat.gracefulshutdown.shutdown-wait-seconds:20}")
    private int shutdownWaitSeconds;

    /**
     * The health checker which is used to manipulate the health actuator endpoint.
     */
    @Autowired
    private GracefulShutdownHealthCheck healthCheck;

    /**
     * The tomcat connector.
     *
     * @param connector
     */
    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    /**
     * The {@link ContextClosedEvent} which is used to handle the graceful shutdown.
     * <p>
     * The shutdown is delayed till all pending requests are handled by the webserver.
     * <p>
     * At the beginning of the shutdown process the actuator endpoint "/actuator/health"
     * is set from status UP to status DOWN.
     *
     * @param event The {@link ContextClosedEvent} to gracefully shutdown.
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOG.info("context close event happened");

        healthCheck.setStatusDown();

        try {
            Thread.sleep(preWaitSeconds * MILLIS_PER_SECOND);

            this.connector.pause();
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {

                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(shutdownWaitSeconds, TimeUnit.SECONDS)) {
                    LOG.warn("Tomcat thread pool did not shut down gracefully within {} seconds. Proceeding with forceful shutdown", shutdownWaitSeconds);
                    threadPoolExecutor.shutdownNow();
                } else {
                    LOG.warn("The application gracefully shuts down now. All pending requests were served.");
                }
            }
        } catch (InterruptedException ex) {
            LOG.warn("InterruptedException during shutdown happened");
            Thread.currentThread().interrupt();
        }

    }

}
