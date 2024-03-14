package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3AdapterTest {

    private final DocumentStorageFileRepository documentStorageFileRepository = mock(DocumentStorageFileRepository.class);
    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);
    private final ProcessConfigApi processConfigApi = mock(ProcessConfigApi.class);
    private final List<String> supportedExtensions = List.of("application/pdf", "text/plain");

    private final S3Adapter s3Adapter = new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, processConfigApi, supportedExtensions);


    // test data
    private final List<String> filepaths = List.of("path/to/file.txt");
    private final String processDefinition = "processDefinition";

    @Test
    void test_load_single_file_successfully() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior
        when(documentStorageFileRepository.getFile(anyString(), anyInt(), any())).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(), anyString())).thenReturn(Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        // Invoke the method under test
        List<FileContent> result = s3Adapter.loadFiles(filepaths, processDefinition);

        // Assert the result
        assertThat(result).hasSize(1);
        final FileContent fileContent = result.get(0);
        assertThat(fileContent.getMimeType()).isEqualTo("text/plain");
        assertThat(fileContent.getName()).isEqualTo("file.txt");
        assertThat(fileContent.getData()).isEqualTo("fileContent".getBytes());
    }

    @Test
    void test_load_single_file_from_domain_specific_s3_successfully() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior
        when(documentStorageFileRepository.getFile(anyString(), anyInt(), anyString())).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(), anyString())).thenReturn(Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenReturn(ProcessConfigTO.builder()
                .configs(List.of(ConfigEntryTO.builder()
                        .key("app_file_s3_sync_config")
                        .value("s3storage")
                        .build()))
                .build());

        // Invoke the method under test
        List<FileContent> result = s3Adapter.loadFiles(filepaths, processDefinition);

        // Assert the result
        assertThat(result).hasSize(1);
        final FileContent fileContent = result.get(0);
        assertThat(fileContent.getMimeType()).isEqualTo("text/plain");
        assertThat(fileContent.getName()).isEqualTo("file.txt");
        assertThat(fileContent.getData()).isEqualTo("fileContent".getBytes());
    }

    @Test
    void test_load_file_throws_bpmn_error_for_unsupported_types() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        // Set up mock behavior
        when(documentStorageFileRepository.getFile(anyString(), anyInt(), any())).thenReturn("fileContent".getBytes());
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(), anyString())).thenReturn(Mono.just(Collections.emptySet()));
        when(processConfigApi.getProcessConfig(anyString())).thenThrow(new RuntimeException("Process Config does not exist"));

        final S3Adapter s3Adapter = new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, processConfigApi, List.of("application/pdf"));

        // Assert the result
        assertThatThrownBy(() -> s3Adapter.loadFiles(filepaths, processDefinition))
                .isInstanceOf(BpmnError.class)
                .extracting("errorCode", "errorMessage")
                .containsExactly("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: path/to/file.txt");
    }


}
