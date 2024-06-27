package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

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
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import org.junit.jupiter.api.Test;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

class S3AdapterTest {

    private static final String DEFAULT_S3_URL = "defaultURL";
    private static final String DOMAIN_SPECIFIC_S3_URL = "domainSpecificURL";

    private static final DataSize ALLOWED_FILE_SIZE = DataSize.ofMegabytes(100);
    private static final DataSize ALLOWED_BATCH_SIZE = DataSize.ofMegabytes(110);
    private static final long TOO_LARGE_FILE_SIZE = ALLOWED_FILE_SIZE.toBytes() + 1L; // 1 byte over allowed

    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);
    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);
    private final ProcessConfigClient processConfigClient = mock(ProcessConfigClient.class);
    private final ProcessConfigApi processConfigApi = spy(new ProcessConfigApiImpl(processConfigClient));
    private final Map<String, String> supportedExtensions = Map.of("pdf", "application/pdf", "txt", "text/plain");
    private final FileService fileService = new FileService(supportedExtensions, ALLOWED_FILE_SIZE, ALLOWED_BATCH_SIZE);
    private final S3DomainProvider s3DomainProvider = processConfigApi::getAppFileS3SyncConfig;
    private final S3StorageUrlProvider s3StorageUrlProvider = new S3StorageUrlProvider(s3DomainProvider, DEFAULT_S3_URL);

    private final S3Adapter s3Adapter = new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, fileService, s3StorageUrlProvider);

    // test data
    private final List<String> filepaths = List.of("path/to/file.txt");
    private final String processDefinition = "processDefinition";
    private final String fileContext = "fileContext";

    @Test
    void test_load_single_file_successfully()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior

        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(documentStorageFileRepository.getFile(startsWith(fileContext), anyInt(), startsWith(DEFAULT_S3_URL))).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(startsWith(fileContext), anyString())).thenReturn(
                Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        // Invoke the method under test
        List<FileContent> result = s3Adapter.loadFiles(filepaths, fileContext, processDefinition);

        // Assert the result
        assertThat(result).hasSize(1);
        final FileContent fileContent = result.get(0);
        assertThat(fileContent.getMimeType()).isEqualTo("text/plain");
        assertThat(fileContent.getName()).isEqualTo("file.txt");
        assertThat(fileContent.getData()).isEqualTo("fileContent".getBytes());
    }

    @Test
    void test_load_single_file_from_domain_specific_s3_successfully()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior

        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(documentStorageFileRepository.getFile(startsWith(fileContext), anyInt(), startsWith(DOMAIN_SPECIFIC_S3_URL))).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(startsWith(fileContext), anyString())).thenReturn(
                Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenReturn(ProcessConfigTO.builder()
                .configs(List.of(ConfigEntryTO.builder()
                        .key("app_file_s3_sync_config")
                        .value(DOMAIN_SPECIFIC_S3_URL)
                        .build()))
                .build());

        // Invoke the method under test
        List<FileContent> result = s3Adapter.loadFiles(filepaths, fileContext, processDefinition);

        // Assert the result
        assertThat(result).hasSize(1);
        final FileContent fileContent = result.get(0);
        assertThat(fileContent.getMimeType()).isEqualTo("text/plain");
        assertThat(fileContent.getName()).isEqualTo("file.txt");
        assertThat(fileContent.getData()).isEqualTo("fileContent".getBytes());
    }

    @Test
    void test_load_file_throws_bpmn_error_for_unsupported_types()
            throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior
        when(documentStorageFileRepository.getFileSize(anyString(), anyString())).thenReturn(just(1_000_000L));
        when(documentStorageFileRepository.getFile(startsWith(fileContext), anyInt(), startsWith(DEFAULT_S3_URL))).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(startsWith(fileContext), startsWith(DEFAULT_S3_URL))).thenReturn(Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));
        final Map<String, String> extensions = Map.of("foo", "baa");
        final FileService tFileService = new FileService(extensions, DataSize.ofMegabytes(50), DataSize.ofMegabytes(100));

        final S3Adapter s3Adapter = new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, tFileService, s3StorageUrlProvider);

        // Assert the result
        assertThatThrownBy(() -> s3Adapter.loadFiles(filepaths, fileContext, processDefinition))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: fileContext/path/to/file.txt");
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

        try {
            this.s3Adapter.loadFiles(filePaths, fileContext, processDefinition);
        } catch (BpmnError bpmnError) {
            DataSize sum = DataSize.ofBytes(ALLOWED_FILE_SIZE.toBytes() + DataSize.ofMegabytes(20).toBytes());
            String expectedMessage = String.format("Batch size of %d MB is too large. Allowed are %d MB.", sum.toMegabytes(), ALLOWED_BATCH_SIZE.toMegabytes());
            String actualMessage = bpmnError.getErrorMessage();

//            assertEquals(expectedMessage, actualMessage);

            assertEquals("BATCH_SIZE_ERROR", bpmnError.getErrorCode());
        }
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

        try {
            this.s3Adapter.loadFiles(filePaths, fileContext, processDefinition);
        } catch (BpmnError bpmnError) {
            String expectedMessage = String.format("The following files exceed the maximum size:%n%s/%s: %d MB", fileContext, pathLargeFile,
                    DataSize.ofBytes(TOO_LARGE_FILE_SIZE).toMegabytes());
            String actualMessage = bpmnError.getErrorMessage();

            assertEquals(expectedMessage, actualMessage);

            assertEquals("FILE_SIZE_ERROR", bpmnError.getErrorCode());
        }
    }

}
