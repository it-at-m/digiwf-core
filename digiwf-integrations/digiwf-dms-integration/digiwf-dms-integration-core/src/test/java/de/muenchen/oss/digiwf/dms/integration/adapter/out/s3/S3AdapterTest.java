package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;

class S3AdapterTest {

    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);

    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);

    private Map<String,String> supportedExtensions = new HashMap<>();

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        supportedExtensions.put("pdf", "application/pdf");
        supportedExtensions.put("png", "image/png");
        supportedExtensions.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        s3Adapter = new S3Adapter(documentStorageFileRepository,documentStorageFolderRepository,supportedExtensions);
    }

    @Test
    void testLoadFileFromFilePath() throws IOException, DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String pngPath = "test/digiwf_logo.png";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;
        final String fullPngPath = fileContext + "/" + pngPath;

        final List<String> filePaths = List.of(pdfPath,pngPath);

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullPdfPath,3)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath,3)).thenReturn(testPng);

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext);

        final Content pdfContent = new Content("pdf","test-pdf",testPdf);
        final Content pngContent = new Content("png","digiwf_logo",testPng);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
    }

    @Disabled("This test is disabled because the feature domain specific s3 storage url is not implemented yet. See https://github.com/it-at-m/digiwf-core/issues/734")
    @Test
    void testLoadFileFromFilePathWithStorageUrl() throws IOException, DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String pngPath = "test/digiwf_logo.png";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;
        final String fullPngPath = fileContext + "/" + pngPath;

        final List<String> filePaths = List.of(pdfPath,pngPath);

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullPdfPath,3,"S3Url")).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath,3,"S3Url")).thenReturn(testPng);

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext);

        final Content pdfContent = new Content("pdf","test-pdf",testPdf);
        final Content pngContent = new Content("png","digiwf_logo",testPng);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
    }


    @Test
    void testLoadFileFromFolderPath() throws IOException, DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String folderPath = "test/";
        final String fileContext = "files";
        final String fullFolderPath = fileContext + "/" + folderPath;

        final String fullPdfPath = "files/test/test-pdf.pdf";
        final String fullPngPath = "files/test/digiwf_logo.png";
        final String fullWordPath = "files/test/test-word.docx";

        final List<String> paths = List.of(folderPath);

        Set<String> filesPaths = new HashSet<>(List.of(fullPdfPath, fullPngPath, fullWordPath));

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();
        final byte[] testWord = new ClassPathResource(fullWordPath).getInputStream().readAllBytes();

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath)).thenReturn((just(filesPaths)));

        when(documentStorageFileRepository.getFile(fullPdfPath,3)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath,3)).thenReturn(testPng);
        when(documentStorageFileRepository.getFile(fullWordPath,3)).thenReturn(testWord);

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext);

        final Content pdfContent = new Content("pdf","test-pdf",testPdf);
        final Content pngContent = new Content("png","digiwf_logo",testPng);
        final Content wordContent = new Content("docx","test-word",testWord);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
        assertTrue(contents.contains(wordContent));
    }

    @Disabled("This test is disabled because the feature domain specific s3 storage url is not implemented yet. See https://github.com/it-at-m/digiwf-core/issues/734")
    @Test
    void testLoadFileFromFolderPathWithStorageUrl() throws IOException, DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String folderPath = "test/";
        final String fileContext = "files";
        final String fullFolderPath = fileContext + "/" + folderPath;

        final String fullPdfPath = "files/test/test-pdf.pdf";
        final String fullPngPath = "files/test/digiwf_logo.png";
        final String fullWordPath = "files/test/test-word.docx";

        final List<String> paths = List.of(folderPath);

        Set<String> filesPaths = new HashSet<>(List.of(fullPdfPath, fullPngPath, fullWordPath));

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();
        final byte[] testWord = new ClassPathResource(fullWordPath).getInputStream().readAllBytes();

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath,"S3Url")).thenReturn((just(filesPaths)));

        when(documentStorageFileRepository.getFile(fullPdfPath,3,"S3Url")).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath,3,"S3Url")).thenReturn(testPng);
        when(documentStorageFileRepository.getFile(fullWordPath,3,"S3Url")).thenReturn(testWord);

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext);

        final Content pdfContent = new Content("pdf","test-pdf",testPdf);
        final Content pngContent = new Content("png","digiwf_logo",testPng);
        final Content wordContent = new Content("docx","test-word",testWord);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
        assertTrue(contents.contains(wordContent));
    }

    @Test
    void testLoadFileFromFilePathThrowsDocumentStorageException() throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;

        final List<String> filePaths = List.of(pdfPath);

        when(documentStorageFileRepository.getFile(fullPdfPath,3)).thenThrow(new DocumentStorageException("Some error", new RuntimeException("Some error")));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext));

        String expectedMessage = "An file could not be loaded from url: " + fullPdfPath;
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("LOAD_FILE_FAILED",bpmnError.getErrorCode());
    }

    @Test
    void testLoadFileFromFolderPathThrowsDocumentStorageServerErrorException() throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String folderPath = "test/";
        final String fileContext = "files";

        final String fullFolderPath = fileContext + "/" + folderPath;

        final List<String> filePaths = List.of(folderPath);

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath)).thenThrow(new DocumentStorageServerErrorException("Some error", new RuntimeException("Some error")));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext));

        String expectedMessage = "An folder could not be loaded from url: " + fullFolderPath;
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("LOAD_FOLDER_FAILED",bpmnError.getErrorCode());
    }

    @Test
    void testLoadFileFromFilePathThrowsUnsupportedFileTypeException() throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, IOException {

        final String htmlPath = "fail/test-html.html";
        final String fileContext = "files";

        final String fullHtmlPath = fileContext + "/" + htmlPath;

        final List<String> filePaths = List.of(htmlPath);

        final byte[] testHtml = new ClassPathResource(fullHtmlPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullHtmlPath,3)).thenReturn(testHtml);

        try {
            this.s3Adapter.loadFiles(filePaths, fileContext);
        } catch (BpmnError bpmnError) {
            String expectedMessage = "The type of this file is not supported: " + fullHtmlPath;
            String actualMessage = bpmnError.getErrorMessage();

            assertEquals(expectedMessage, actualMessage);

            assertEquals("FILE_TYPE_NOT_SUPPORTED",bpmnError.getErrorCode());
        }

    }
}
