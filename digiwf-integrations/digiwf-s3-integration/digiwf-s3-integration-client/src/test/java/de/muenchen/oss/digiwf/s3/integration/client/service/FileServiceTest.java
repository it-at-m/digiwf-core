package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        final Map<String, String> fileExtensions = new HashMap<>();
        fileExtensions.put("pdf", "application/pdf");
        fileExtensions.put("txt", "text/plain");
        fileService = new FileService(fileExtensions, DataSize.ofKilobytes(10), DataSize.ofMegabytes(5));
    }

    @Test
    void testIsSupported() {
        assertTrue(fileService.isSupported("pdf"));
        assertFalse(fileService.isSupported("exe"));
    }

    @Test
    void testGetFileExtension() {
        assertEquals("pdf", fileService.getFileExtension("application/pdf"));
    }

    @Test
    void testGetFileExtensionThrowsException() {
        assertThrows(NoFileTypeException.class, () -> fileService.getFileExtension("application/unknown"));
    }

    @Test
    void testDetectFileType() {
        final byte[] fileContent = "Hello, universe!".getBytes();
        assertEquals("text/plain", fileService.detectFileType(fileContent));
    }

    @Test
    void testSupportCheckWithEmptyMap() {
        final FileService emptyService = new FileService(new HashMap<>(), DataSize.ofMegabytes(100), DataSize.ofMegabytes(500));
        assertTrue(emptyService.isSupported("anyType"));
    }

    @Test
    void testIsValidFileSizeByteArray() {
        assertTrue(fileService.isValidFileSize(new byte[]{1}));
    }

    @Test
    void testIsValidFileSizeLong() {
        assertFalse(fileService.isValidFileSize(10000000L)); // Assuming maxFileSize is set to 10MB
    }

    @Test
    void testGetTotalBatchSize() {
        Map<String, Long> fileSizesWithPaths = new HashMap<>();
        fileSizesWithPaths.put("path/to/file1", 1024L);
        fileSizesWithPaths.put("path/to/file2", 2048L);

        DataSize expectedTotalSize = DataSize.ofBytes(3072); // 1024 + 2048

        assertEquals(expectedTotalSize, fileService.getTotalBatchSize(fileSizesWithPaths));
    }

    @Test
    void testGetOversizedFiles() {
        Map<String, Long> fileSizesWithPaths = new HashMap<>();
        fileSizesWithPaths.put("path/to/smallFile.txt", 500L);
        fileSizesWithPaths.put("path/to/largeFile.pdf", 15000000L);
        fileSizesWithPaths.put("path/to/mediumFile.docx", 7000L);

        Map<String, Long> expectedOversizedFiles = new HashMap<>();
        expectedOversizedFiles.put("path/to/largeFile.pdf", 15000000L);

        Map<String, Long> oversizedFiles = fileService.getOversizedFiles(fileSizesWithPaths);

        assertEquals(expectedOversizedFiles, oversizedFiles);
    }

}
