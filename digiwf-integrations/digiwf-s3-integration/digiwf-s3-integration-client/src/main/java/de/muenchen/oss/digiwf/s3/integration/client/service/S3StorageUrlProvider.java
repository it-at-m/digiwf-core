package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Slf4j
public class S3StorageUrlProvider {

    private final S3DomainProvider s3DomainProvider;

    @Getter
    private final String defaultDocumentStorageUrl;

    public S3StorageUrlProvider(final S3DomainProvider s3DomainProvider, final String defaultDocumentStorageUrl) {
        this.s3DomainProvider = s3DomainProvider;
        this.defaultDocumentStorageUrl = defaultDocumentStorageUrl;
    }

    public S3StorageUrlProvider(final String defaultDocumentStorageUrl) {
        this(processDefinition -> Optional.empty(), defaultDocumentStorageUrl);
    }

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
