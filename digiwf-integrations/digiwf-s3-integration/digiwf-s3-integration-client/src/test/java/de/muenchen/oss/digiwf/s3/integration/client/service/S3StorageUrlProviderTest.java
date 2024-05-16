package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class S3StorageUrlProviderTest {

    private final S3DomainProvider s3DomainProvider = mock(S3DomainProvider.class);

    private static final String DEFAULT_DOCUMENT_STORAGE_URL = "http://default-storage-url.com";
    private static final String PROCESS_DEFINITION_ID = "processDefinitionId";
    private static final String DOMAIN_SPECIFIC_STORAGE_URL = "http://domain-specific-url.com";

    private S3StorageUrlProvider s3StorageUrlProviderWithDefault;
    private S3StorageUrlProvider s3StorageUrlProviderWithoutDefault;

    @BeforeEach
    public void setUp() {
        s3StorageUrlProviderWithDefault = new S3StorageUrlProvider(s3DomainProvider, DEFAULT_DOCUMENT_STORAGE_URL);
        s3StorageUrlProviderWithoutDefault = new S3StorageUrlProvider(s3DomainProvider, null);
    }

    @Test
    public void testProvideS3StorageUrl_DomainSpecificUrl() throws PropertyNotSetException {
        // Arrange
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.of(DOMAIN_SPECIFIC_STORAGE_URL));

        // Act
        String result = s3StorageUrlProviderWithDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID);

        // Assert
        assertEquals(DOMAIN_SPECIFIC_STORAGE_URL, result);
    }

    @Test
    public void testProvideS3StorageUrl_DefaultUrl() throws PropertyNotSetException {
        // Arrange
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.empty());

        // Act
        String result = s3StorageUrlProviderWithDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID);

        // Assert
        assertEquals(DEFAULT_DOCUMENT_STORAGE_URL, result);
    }

    @Test
    public void testProvideS3StorageUrl_NoUrls() {
        // Arrange
        when(s3DomainProvider.provideDomainSpecificS3StorageUrl(PROCESS_DEFINITION_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        PropertyNotSetException exception = assertThrows(PropertyNotSetException.class, () ->
                s3StorageUrlProviderWithoutDefault.provideS3StorageUrl(PROCESS_DEFINITION_ID)
        );

        assertEquals("Default document storage is not set. Make sure the property de.muenchen.oss.digiwf.s3.document-storage-url is set.",
                exception.getMessage());
    }
}
