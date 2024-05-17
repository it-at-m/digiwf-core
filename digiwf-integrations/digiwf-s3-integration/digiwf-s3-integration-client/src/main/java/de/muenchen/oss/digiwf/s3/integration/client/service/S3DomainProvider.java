package de.muenchen.oss.digiwf.s3.integration.client.service;

import java.util.Optional;

/**
 * Implementors obtain a function that provides an S3 storage URL if it is configured for a given process definition.
 */
@FunctionalInterface
public interface S3DomainProvider {

    /**
     * Provides an S3 storage URL if it is configured for a given process definition.
     *
     * @param processDefinitionId The {@link String} id of a process definition.
     * @return An {@link Optional} containing an S3 storage URL if it is configured.
     */
    Optional<String> provideDomainSpecificS3StorageUrl(String processDefinitionId);
}
