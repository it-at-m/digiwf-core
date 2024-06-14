package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Provides the S3 storage URL either configured in a process configuration of a given process definition or the default URL.
 */
@Slf4j
public class S3StorageUrlProvider {

    private final S3DomainProvider s3DomainProvider;

    @Getter
    private final String defaultDocumentStorageUrl;

    /**
     * Constructor for client users which obtain their own {@link S3DomainProvider}.
     *
     * @param s3DomainProvider          The {@link S3DomainProvider} to use for fetching domain-specific S3 URLs.
     * @param defaultDocumentStorageUrl The default S3 storage URL to use if no domain-specific URL is available.
     */
    public S3StorageUrlProvider(final S3DomainProvider s3DomainProvider, final String defaultDocumentStorageUrl) {
        this.s3DomainProvider = s3DomainProvider;
        this.defaultDocumentStorageUrl = defaultDocumentStorageUrl;
    }

    /**
     * Provides an S3 storage URL either configured in a process configuration of a given process definition or the default URL.
     *
     * @param processDefinitionId The {@link String} process definition id for which the S3 storage URL will be provided.
     * @return The S3 storage URL.
     * @throws PropertyNotSetException Thrown if the property 'de.muenchen.oss.digiwf.s3.document-storage-url' is unset.
     */
    public String provideS3StorageUrl(final String processDefinitionId) throws PropertyNotSetException {
        final Optional<String> domainSpecificStorageUrl = s3DomainProvider.provideDomainSpecificS3StorageUrl(processDefinitionId);
        if (domainSpecificStorageUrl.isPresent()) return domainSpecificStorageUrl.get();
        if (StringUtils.isNotBlank(this.defaultDocumentStorageUrl)) {
            return this.defaultDocumentStorageUrl;
        }
        final String message = "Default document storage is not set. Make sure the property de.muenchen.oss.digiwf.s3.document-storage-url is set.";
        log.error(message);
        throw new PropertyNotSetException(message);
    }

}
