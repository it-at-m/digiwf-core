package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3StorageUrlProviderTest {

    private final S3DomainProvider s3DomainProvider = mock(S3DomainProvider.class);

    private static final String DEFAULT_DOCUMENT_STORAGE_URL = "default-storage-url";
    private static final String PROCESS_DEFINITION_ID = "processDefinitionId";
    private static final String DOMAIN_SPECIFIC_STORAGE_URL = "domain-specific-url";

    private S3StorageUrlProvider s3StorageUrlProviderWithDefault;
    private S3StorageUrlProvider s3StorageUrlProviderWithoutDefault;

    @BeforeEach
    void setUp() {
        s3StorageUrlProviderWithDefault = new S3StorageUrlProvider(s3DomainProvider, DEFAULT_DOCUMENT_STORAGE_URL);
        s3StorageUrlProviderWithoutDefault = new S3StorageUrlProvider(s3DomainProvider, null);
    }

    @Test
    void testProvideS3StorageUrl_DomainSpecificUrl() throws PropertyNotSetException {
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.of(DOMAIN_SPECIFIC_STORAGE_URL));
        String result = s3StorageUrlProviderWithDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID);
        assertEquals(DOMAIN_SPECIFIC_STORAGE_URL, result);
    }

    @Test
    void testProvideS3StorageUrl_DefaultUrl() throws PropertyNotSetException {
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.empty());
        String result = s3StorageUrlProviderWithDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID);
        assertEquals(DEFAULT_DOCUMENT_STORAGE_URL, result);
    }

    @Test
    void testProvideS3StorageUrl_NoUrls() {
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.empty());
        PropertyNotSetException exception = assertThrows(PropertyNotSetException.class, () ->
                s3StorageUrlProviderWithoutDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID)
        );

        assertEquals("Default document storage is not set. Make sure the property de.muenchen.oss.digiwf.s3.document-storage-url is set.",
                exception.getMessage());
    }
}
