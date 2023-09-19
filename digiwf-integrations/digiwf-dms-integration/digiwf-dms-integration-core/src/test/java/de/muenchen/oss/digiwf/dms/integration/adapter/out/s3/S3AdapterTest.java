package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.just;

class S3AdapterTest {

    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);

    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);

    private final List<String> supportedExtensions = List.of("application/pdf","image/png","application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    private final ProcessConfigApi processConfigApi = mock((ProcessConfigApi.class));

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(documentStorageFileRepository,documentStorageFolderRepository,supportedExtensions,processConfigApi);
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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),new ArrayList<>()));

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext, "processInstance");

        final Content pdfContent = new Content("application/pdf","test-pdf",testPdf);
        final Content pngContent = new Content("image/png","digiwf_logo",testPng);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
    }

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),List.of(new ConfigEntryTO("app_file_s3_sync_config","S3Url"))));

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext, "processInstance");

        final Content pdfContent = new Content("application/pdf","test-pdf",testPdf);
        final Content pngContent = new Content("image/png","digiwf_logo",testPng);

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),new ArrayList<>()));

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext, "processInstance");

        final Content pdfContent = new Content("application/pdf","test-pdf",testPdf);
        final Content pngContent = new Content("image/png","digiwf_logo",testPng);
        final Content wordContent = new Content("application/vnd.openxmlformats-officedocument.wordprocessingml.document","test-word",testWord);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
        assertTrue(contents.contains(wordContent));
    }

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),List.of(new ConfigEntryTO("app_file_s3_sync_config","S3Url"))));

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext, "processInstance");

        final Content pdfContent = new Content("application/pdf","test-pdf",testPdf);
        final Content pngContent = new Content("image/png","digiwf_logo",testPng);
        final Content wordContent = new Content("application/vnd.openxmlformats-officedocument.wordprocessingml.document","test-word",testWord);

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),new ArrayList<>()));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext, "processInstance"));

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),new ArrayList<>()));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext, "processInstance"));

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
        when(processConfigApi.getProcessConfig(any())).thenReturn(new ProcessConfigTO("key","statusdocument",new ArrayList<>(),new ArrayList<>()));

        try {
            this.s3Adapter.loadFiles(filePaths, fileContext, "processInstance");
        } catch (BpmnError bpmnError) {
            String expectedMessage = "The type of this file is not supported: " + fullHtmlPath;
            String actualMessage = bpmnError.getErrorMessage();

            assertEquals(expectedMessage, actualMessage);

            assertEquals("FILE_TYPE_NOT_SUPPORTED",bpmnError.getErrorCode());
        }

    }
}