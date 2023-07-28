package de.muenchen.oss.digiwf.s3.integration.configuration;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.CleanUpExpiredFilesInPort;
import de.muenchen.oss.digiwf.s3.integration.application.port.in.CleanUpUnusedFoldersInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
    prefix = "de.muenchen.oss.digiwf.s3.cronjob.cleanup",
    name = {
        "expired-files",
        "unused-files"
    }
)
public class CronJobConfiguration {

  private final CleanUpExpiredFilesInPort cleanUpExpiredFiles;
  private final CleanUpUnusedFoldersInPort cleanUpUnusedFolders;

  @Scheduled(cron = "${io.muenchendigital.digiwf.s3.cronjob.cleanup.expired-files}")
  public void cronJobDefinitionCleanUpExpiredFolders() {
    this.cleanUpExpiredFiles.cleanUpExpiredFolders();
  }

  @Scheduled(cron = "${io.muenchendigital.digiwf.s3.cronjob.cleanup.unused-files}")
  public void cronJobCleanUpUnusedFolders() {
    this.cleanUpUnusedFolders.cleanUpUnusedFolders();
  }

}

