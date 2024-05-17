package de.muenchen.oss.digiwf.process.api.config.api;

import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;

import java.util.Optional;

/**
 * ProcessConfigApi is the api to obtain the process configuration.
 */
public interface ProcessConfigApi {

    /**
     * Obtain a process configuration for a given process definition id from digiwf-engine.
     *
     * @param processDefinitionId the process definition id
     * @return the process configuration
     */
    ProcessConfigTO getProcessConfig(final String processDefinitionId);

    /**
     * Retrieves a configuration value for a given key and process definition ID from the digiwf-engine.
     *
     * @param key                 The key of the configuration value to retrieve.
     * @param processDefinitionId The ID of the process definition for which the configuration value is sought.
     * @return An {@link Optional} containing the configuration value as a {@code String}, or an empty {@link Optional} if no value is found.
     */
    Optional<String> getProcessConfigValue(final String key, final String processDefinitionId);

    /**
     * Retrieves a domain-specific S3 storage URL for a given process definition id from digiwf-engine.
     *
     * @param processDefinitionId the process definition id
     * @return the domain-specific S3 storage URL if configured
     */
    Optional<String> getAppFileS3SyncConfig(String processDefinitionId);
}
