package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigApiImpl;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigClient;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3DomainProvider;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.unit.DataSize;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

class S3AdapterTest {

    private static final String DEFAULT_S3_URL = "defaultURL";
    private static final String DOMAIN_SPECIFIC_S3_URL = "domainSpecificURL";

    private static final DataSize ALLOWED_FILE_SIZE = DataSize.ofMegabytes(100);
    private static final DataSize ALLOWED_BATCH_SIZE = DataSize.ofMegabytes(110);
    private static final long TOO_LARGE_FILE_SIZE = ALLOWED_FILE_SIZE.toBytes() + DataSize.ofMegabytes(1L).toBytes(); // 1 Mbyte over allowed

    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);

    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);
    private final ProcessConfigClient processConfigClient = mock(ProcessConfigClient.class);
    private final ProcessConfigApi processConfigApi = spy(new ProcessConfigApiImpl(processConfigClient));
    private final S3DomainProvider s3DomainProvider = processConfigApi::getAppFileS3SyncConfig;
    private final S3StorageUrlProvider s3StorageUrlProvider = new S3StorageUrlProvider(s3DomainProvider, DEFAULT_S3_URL);

    private final Map<String, String> supportedExtensions = Map.of("pdf", "application/pdf",
            "png", "image/png",
            "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    private final FileService fileService = new FileService(supportedExtensions, ALLOWED_FILE_SIZE, ALLOWED_BATCH_SIZE);
    private final String processDefinitionId = "processDefinition";

    private S3Adapter s3Adapter;

    @BeforeEach
    void setup() {
        s3Adapter = new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, fileService, s3StorageUrlProvider);
    }

    @Test
    void testLoadFileFromFilePath()
            throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String pngPath = "test/digiwf_logo.png";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;
        final String fullPngPath = fileContext + "/" + pngPath;

        final List<String> filePaths = List.of(pdfPath, pngPath);

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullPdfPath, 3, DEFAULT_S3_URL)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath, 3, DEFAULT_S3_URL)).thenReturn(testPng);
        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext, processDefinitionId);

        final Content pdfContent = new Content("pdf", "test-pdf", testPdf);
        final Content pngContent = new Content("png", "digiwf_logo", testPng);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
    }

    @Test
    void testLoadFileFromFilePathWithStorageUrl()
            throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String pngPath = "test/digiwf_logo.png";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;
        final String fullPngPath = fileContext + "/" + pngPath;

        final List<String> filePaths = List.of(pdfPath, pngPath);

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullPdfPath, 3, DOMAIN_SPECIFIC_S3_URL)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath, 3, DOMAIN_SPECIFIC_S3_URL)).thenReturn(testPng);
        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(processConfigApi.getProcessConfig(anyString())).thenReturn(ProcessConfigTO.builder()
                .configs(List.of(ConfigEntryTO.builder()
                        .key("app_file_s3_sync_config")
                        .value(DOMAIN_SPECIFIC_S3_URL)
                        .build()))
                .build());

        final List<Content> contents = this.s3Adapter.loadFiles(filePaths, fileContext, processDefinitionId);

        final Content pdfContent = new Content("pdf", "test-pdf", testPdf);
        final Content pngContent = new Content("png", "digiwf_logo", testPng);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
    }

    @Test
    void testLoadFileFromFolderPath()
            throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String folderPath = "test/";
        final String fileContext = "files";
        final String fullFolderPath = fileContext + "/" + folderPath;

        final String fullPdfPath = "files/test/test-pdf.pdf";
        final String fullPngPath = "files/test/digiwf_logo.png";
        final String fullWordPath = "files/test/test-word.docx";

        final List<String> paths = List.of(folderPath);

        final Set<String> filesPaths = new HashSet<>(List.of(fullPdfPath, fullPngPath, fullWordPath));

        final byte[] testPdf = new ClassPathResource(fullPdfPath).getInputStream().readAllBytes();
        final byte[] testPng = new ClassPathResource(fullPngPath).getInputStream().readAllBytes();
        final byte[] testWord = new ClassPathResource(fullWordPath).getInputStream().readAllBytes();

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath, DEFAULT_S3_URL)).thenReturn((just(filesPaths)));

        when(documentStorageFileRepository.getFile(fullPdfPath, 3, DEFAULT_S3_URL)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath, 3, DEFAULT_S3_URL)).thenReturn(testPng);
        when(documentStorageFileRepository.getFile(fullWordPath, 3, DEFAULT_S3_URL)).thenReturn(testWord);
        when(documentStorageFolderRepository.getAllFileSizesInFolderRecursively(anyString(), anyString())).thenReturn(just(Map.of("", 1_000_000L)));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext, processDefinitionId);

        final Content pdfContent = new Content("pdf", "test-pdf", testPdf);
        final Content pngContent = new Content("png", "digiwf_logo", testPng);
        final Content wordContent = new Content("docx", "test-word", testWord);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
        assertTrue(contents.contains(wordContent));
    }

    @Test
    void testLoadFileFromFolderPathWithStorageUrl()
            throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

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

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath, DOMAIN_SPECIFIC_S3_URL)).thenReturn((just(filesPaths)));

        when(documentStorageFileRepository.getFile(fullPdfPath, 3, DOMAIN_SPECIFIC_S3_URL)).thenReturn(testPdf);
        when(documentStorageFileRepository.getFile(fullPngPath, 3, DOMAIN_SPECIFIC_S3_URL)).thenReturn(testPng);
        when(documentStorageFileRepository.getFile(fullWordPath, 3, DOMAIN_SPECIFIC_S3_URL)).thenReturn(testWord);
        when(documentStorageFolderRepository.getAllFileSizesInFolderRecursively(anyString(), anyString())).thenReturn(just(Map.of("", 1_000_000L)));
        when(processConfigApi.getProcessConfig(anyString())).thenReturn(ProcessConfigTO.builder()
                .configs(List.of(ConfigEntryTO.builder()
                        .key("app_file_s3_sync_config")
                        .value(DOMAIN_SPECIFIC_S3_URL)
                        .build()))
                .build());

        final List<Content> contents = this.s3Adapter.loadFiles(paths, fileContext, processDefinitionId);

        final Content pdfContent = new Content("pdf", "test-pdf", testPdf);
        final Content pngContent = new Content("png", "digiwf_logo", testPng);
        final Content wordContent = new Content("docx", "test-word", testWord);

        assertTrue(contents.contains(pdfContent));
        assertTrue(contents.contains(pngContent));
        assertTrue(contents.contains(wordContent));
    }

    @Test
    void testLoadFileFromFilePathThrowsDocumentStorageException()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String pdfPath = "test/test-pdf.pdf";
        final String fileContext = "files";

        final String fullPdfPath = fileContext + "/" + pdfPath;

        final List<String> filePaths = List.of(pdfPath);

        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(documentStorageFileRepository.getFile(fullPdfPath, 3, DEFAULT_S3_URL)).thenThrow(
                new DocumentStorageException("Some error", new RuntimeException("Some error")));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext, processDefinitionId));

        String expectedMessage = "An file could not be loaded from url: " + fullPdfPath;
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("LOAD_FILE_FAILED", bpmnError.getErrorCode());
    }

    @Test
    void testLoadFileFromFolderPathThrowsDocumentStorageServerErrorException()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {

        final String folderPath = "test/";
        final String fileContext = "files";

        final String fullFolderPath = fileContext + "/" + folderPath;

        final List<String> filePaths = List.of(folderPath);

        when(documentStorageFolderRepository.getAllFileSizesInFolderRecursively(anyString(), anyString())).thenReturn(just(Map.of("", 1_000_000L)));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(fullFolderPath, DEFAULT_S3_URL)).thenThrow(
                new DocumentStorageServerErrorException("Some error", new RuntimeException("Some error")));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.s3Adapter.loadFiles(filePaths, fileContext, processDefinitionId));

        String expectedMessage = "An folder could not be loaded from url: " + fullFolderPath;
        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(expectedMessage, actualMessage);

        assertEquals("LOAD_FOLDER_FAILED", bpmnError.getErrorCode());
    }

    @Test
    void testLoadFileFromFilePathThrowsUnsupportedFileTypeException()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, IOException {

        final String htmlPath = "fail/test-html.html";
        final String fileContext = "files";

        final String fullHtmlPath = fileContext + "/" + htmlPath;

        final List<String> filePaths = List.of(htmlPath);

        final byte[] testHtml = new ClassPathResource(fullHtmlPath).getInputStream().readAllBytes();

        when(documentStorageFileRepository.getFile(fullHtmlPath, 3, DEFAULT_S3_URL)).thenReturn(testHtml);
        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        String expectedMessage = "The type of this file is not supported: " + fullHtmlPath;

        assertThatThrownBy(() -> s3Adapter.loadFiles(filePaths, fileContext, processDefinitionId))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("FILE_TYPE_NOT_SUPPORTED", expectedMessage);
    }

    @Test
    void testLoadFilesThrowsBpmnErrorDueToInvalidBatchSize() throws Exception {
        String pathLargeFile = "path/to/largeFile";
        String pathSmallFile = "path/to/smallFile";
        List<String> filePaths = Arrays.asList(pathLargeFile, pathSmallFile);
        String fileContext = "context";
        String processDefinition = "processDef";

        when(documentStorageFileRepository.getFileSize(eq(fileContext + "/" + pathLargeFile), anyString())).thenReturn(just(ALLOWED_FILE_SIZE.toBytes()));
        when(documentStorageFileRepository.getFileSize(eq(fileContext + "/" + pathSmallFile), anyString())).thenReturn(
                just(DataSize.ofMegabytes(20).toBytes()));

        DataSize sum = DataSize.ofBytes(ALLOWED_FILE_SIZE.toBytes() + DataSize.ofMegabytes(20).toBytes());
        String expectedMessage = String.format("Batch size of %d MB is too large. Allowed are %d MB.", sum.toMegabytes(), ALLOWED_BATCH_SIZE.toMegabytes());

        assertThatThrownBy(() -> s3Adapter.loadFiles(filePaths, fileContext, processDefinition))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("BATCH_SIZE_ERROR", expectedMessage);
    }

    @Test
    void testLoadFilesThrowsBpmnErrorDueToFileExceedingMaxSize() throws Exception {
        String pathLargeFile = "path/to/largeFile";
        String pathSmallFile = "path/to/smallFile";
        List<String> filePaths = Arrays.asList(pathLargeFile, pathSmallFile);
        String fileContext = "context";
        String processDefinition = "processDef";

        when(documentStorageFileRepository.getFileSize(eq(fileContext + "/" + pathLargeFile), anyString())).thenReturn(just(TOO_LARGE_FILE_SIZE));
        when(documentStorageFileRepository.getFileSize(eq(fileContext + "/" + pathSmallFile), anyString())).thenReturn(just(10_240L));

        String expectedMessage = String.format("The following files exceed the maximum size of %d MB:%n%s/%s: %d MB", ALLOWED_FILE_SIZE.toMegabytes(),
                fileContext, pathLargeFile, DataSize.ofBytes(TOO_LARGE_FILE_SIZE).toMegabytes());

        assertThatThrownBy(() -> s3Adapter.loadFiles(filePaths, fileContext, processDefinition))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("FILE_SIZE_ERROR", expectedMessage);
    }

    @Test
    void testTransferContent() throws IOException, DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));
        final String folderPathWithSlash = "folder/";
        final String folderPathWithoutSlash = "folder";
        final String fileContext = "files";
        final byte[] testPdf = new ClassPathResource("files/test/test-pdf.pdf").getInputStream().readAllBytes();
        final Content pdfContent = new Content("pdf", "test-pdf", testPdf);
        final String fullPath = String.format("%s/%s/%s", fileContext, folderPathWithoutSlash, "test-pdf.pdf");
        final String fullPathWrong = String.format("%s/%s//%s", fileContext, folderPathWithoutSlash, "test-pdf.pdf");

        this.s3Adapter.transferContent(List.of(pdfContent), folderPathWithSlash, fileContext, processDefinitionId);
        this.s3Adapter.transferContent(List.of(pdfContent), folderPathWithoutSlash, fileContext, processDefinitionId);

        verify(documentStorageFileRepository, never()).saveFile(eq(fullPathWrong), any(), anyInt(), anyString());
        verify(documentStorageFileRepository, times(2)).saveFile(eq(fullPath), any(), anyInt(), anyString());
    }
}