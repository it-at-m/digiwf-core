package io.muenchendigital.digiwf.cleanup.config;

import io.muenchendigital.digiwf.cleanup.services.CleanupProcessInstancesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configuration and scheduler for cleanup of expired process instances.
 *
 * @author martin.dietrich
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "digiwf.cleanup",
        name = {
                "expired-process-instances"
        }
)
public class CronJobConfiguration {

    private final CleanupProcessInstancesService cleanUpService;

    @Scheduled(cron = "${digiwf.cleanup.expired-process-instances}")
    public void cronJobDefinitionCleanUpDeletedProcessInstances() {
        this.cleanUpService.cleanup();
    }

}

