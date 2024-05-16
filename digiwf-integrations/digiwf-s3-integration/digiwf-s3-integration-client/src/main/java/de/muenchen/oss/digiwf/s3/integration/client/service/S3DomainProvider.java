package de.muenchen.oss.digiwf.s3.integration.client.service;

import java.util.Optional;

public interface S3DomainProvider {

    Optional<String> provideDomainSpecificS3StorageUrl(String processDefinition);
}
