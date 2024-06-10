package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Optional;

import static de.muenchen.oss.digiwf.process.api.config.ProcessConfigConstants.APP_FILE_S3_SYNC_CONFIG;

/**
 * ProcessConfigApiImpl is the api to obtain the process configuration and configuration values.
 */
@RequiredArgsConstructor
public class ProcessConfigApiImpl implements ProcessConfigApi {

    private final ProcessConfigClient processConfigClient;

    @Override
    public ProcessConfigTO getProcessConfig(final String processDefinitionId) {
        return this.processConfigClient.getProcessConfig(processDefinitionId);
    }

    @Override
    public Optional<String> getProcessConfigValue(final String key, final String processDefinitionId) {
        try {
            val processConfigTO = this.getProcessConfig(processDefinitionId);
            return processConfigTO.getConfigs().stream()
                    .filter(cfg -> cfg.getKey().equals(key))
                    .findAny()
                    .map(ConfigEntryTO::getValue);
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getAppFileS3SyncConfig(final String processDefinitionId) {
        return getProcessConfigValue(APP_FILE_S3_SYNC_CONFIG, processDefinitionId);
    }

}
