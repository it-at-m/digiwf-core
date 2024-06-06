package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileExtensionServiceTest {

    private FileExtensionService service;

    @BeforeEach
    void setUp() {
        final Map<String, String> fileExtensions = new HashMap<>();
        fileExtensions.put("pdf", "application/pdf");
        fileExtensions.put("txt", "text/plain");
        service = new FileExtensionService(fileExtensions);
    }

    @Test
    void testIsSupported() {
        assertTrue(service.isSupported("pdf"));
        assertFalse(service.isSupported("exe"));
    }

    @Test
    void testGetFileExtension() {
        assertEquals("pdf", service.getFileExtension("application/pdf"));
    }

    @Test
    void testGetFileExtensionThrowsException() {
        assertThrows(NoFileTypeException.class, () -> service.getFileExtension("application/unknown"));
    }

    @Test
    void testDetectFileType() {
        final byte[] fileContent = "Hello, universe!".getBytes();
        assertEquals("text/plain", service.detectFileType(fileContent));
    }

    @Test
    void testSupportCheckWithEmptyMap() {
        final FileExtensionService emptyService = new FileExtensionService(new HashMap<>());
        assertTrue(emptyService.isSupported("anyType"));
    }
}
