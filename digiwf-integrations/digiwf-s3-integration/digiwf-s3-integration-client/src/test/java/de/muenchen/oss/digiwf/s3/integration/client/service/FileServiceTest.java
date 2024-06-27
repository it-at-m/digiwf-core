package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService service;

    @BeforeEach
    void setUp() {
        final Map<String, String> fileExtensions = new HashMap<>();
        fileExtensions.put("pdf", "application/pdf");
        fileExtensions.put("txt", "text/plain");
        service = new FileService(fileExtensions, DataSize.ofMegabytes(100), DataSize.ofMegabytes(500));
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
        final FileService emptyService = new FileService(new HashMap<>(), DataSize.ofMegabytes(100), DataSize.ofMegabytes(500));
        assertTrue(emptyService.isSupported("anyType"));
    }
}
