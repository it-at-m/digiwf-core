/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.domain.model;

import de.muenchen.oss.digiwf.humantask.process.ProcessTaskConstants;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for a specific process definition.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProcessConfig {

    public static final String INSTANCE_FILE_PATHS_READONLY = "app_instance_file_paths_readonly";
    public static final String INSTANCE_FILE_PATHS = "app_instance_file_paths";
    public static final String INSTANCE_SCHEMA_KEY = "app_instance_schema_key";

    /**
     * This constant holds the configuration key for email addresses that should be notified in case of an incident.
     * These addresses are used to send out alerts or notifications when an incident occurs within the application.
     * <p>
     * The expected format for the email addresses is a comma-separated list of valid email addresses.
     * Example: "admin@example.com,support@example.com"
     */
    public static final String INCIDENT_NOTIFICATION_ADDRESSES = "app_incident_notification_addresses";

    /**
     * key of the process config.
     */
    private String key;

    /**
     * default status dokument.
     */
    private String statusDokument;

    /**
     * status config of the process definition.
     */
    @Builder.Default
    private List<StatusConfig> statusConfig = new ArrayList<>();

    /**
     * dynamic config entries.
     */
    @Builder.Default
    private List<ConfigEntry> configs = new ArrayList<>();

    public String getStatus(final String statusKey) {
        return this.statusConfig.stream()
                .filter(config -> statusKey.equals(config.getKey()))
                .map(StatusConfig::getLabel)
                .findAny()
                .orElse(statusKey);
    }

    public String getConfig(final String configKey) {
        return this.configs.stream()
                .filter(obj -> configKey.equals(obj.getKey()))
                .map(ConfigEntry::getValue)
                .findFirst()
                .orElse(null);
    }

    public boolean isIgnoreFieldsOnStart() {
        return "1".equals(this.getConfig("ignore_fields_on_start"));
    }

    public String getFilePathsReadonly() {
        return this.getConfig(ProcessTaskConstants.FILE_PATHS_READONLY);
    }

    public String getFilePaths() {
        return this.getConfig(ProcessTaskConstants.FILE_PATHS);
    }

    public String getInstanceFilePathsReadonly() {
        return this.getConfig(INSTANCE_FILE_PATHS_READONLY);
    }

    public String getInstanceFilePaths() {
        return this.getConfig(INSTANCE_FILE_PATHS);
    }

    public String getInstanceSchemaKey() {
        return this.getConfig(INSTANCE_SCHEMA_KEY);
    }

    public String getIncidentNotificationAddresses() {
        return this.getConfig(INCIDENT_NOTIFICATION_ADDRESSES);
    }

}
