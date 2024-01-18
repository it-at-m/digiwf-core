package de.muenchen.oss.digiwf.task.service.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.auth.User;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.file.PresignedUrlPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.file.TaskFileConfigResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import de.muenchen.oss.digiwf.task.service.domain.IllegalResourceAccessException;
import de.muenchen.oss.digiwf.task.service.domain.PresignedUrlAction;
import de.muenchen.oss.digiwf.task.service.domain.TaskFileConfig;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static de.muenchen.oss.digiwf.task.service.application.usecase.TestFixtures.generateTask;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class WorkOnTaskFileUseCaseTest {

    private final DocumentStorageFolderRepository documentStorageFolderRepository = mock(DocumentStorageFolderRepository.class);

    private final PresignedUrlPort presignedUrlPort = mock(PresignedUrlPort.class);

    private final TaskFileConfigResolverPort taskFileConfigResolverPort = mock(TaskFileConfigResolverPort.class);

    private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);

    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

    private final WorkOnTaskFileUseCase useCase = new WorkOnTaskFileUseCase(documentStorageFolderRepository,presignedUrlPort,taskFileConfigResolverPort,taskQueryPort,currentUserPort);

    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));

    private String fileContext;
    private final List<String> pathsReadonly = List.of("able/to/read");
    private final List<String> pathsWrite = List.of("able/to/read", "able/to/write");

    @BeforeEach
    void setupMocks() {
        Task task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false);
        when(currentUserPort.getCurrentUser()).thenReturn(user);
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), anyString())).thenReturn(task);
        this.fileContext = UUID.randomUUID().toString();
    }

    @Test
    void getFileNamesDocumentWithStorageUrl() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final Set<String> filesInS3 = Sets.newHashSet(fileContext + "/able/to/read/file1.txt", fileContext + "/able/to/read/secondfile1.pdf");

        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", "syncConfig",pathsWrite,pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString(),anyString())).thenReturn(Mono.just(filesInS3));

        final List<String> listOfNames = useCase.getFileNames("task_0", pathsReadonly.stream().findFirst().orElseThrow());

        assertThat(listOfNames)
                .hasSize(2)
                .contains("file1.txt", "secondfile1.pdf");

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString(),anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getFileNamesDocumentWithoutStorageUrl() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final Set<String> filesInS3 = Sets.newHashSet(fileContext + "/able/to/read/file1.txt", fileContext + "/able/to/read/secondfile1.pdf");

        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", null,pathsWrite,pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenReturn(Mono.just(filesInS3));

        final List<String> listOfNames = useCase.getFileNames("task_0", pathsReadonly.stream().findFirst().orElseThrow());

        assertThat(listOfNames)
                .hasSize(2)
                .contains("file1.txt", "secondfile1.pdf");

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getFileNames_DoesNotIncludeFilesFromSubdirectories()  throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        final Set<String> filesInS3 = Sets.newHashSet(
                fileContext + "/able/to/read/file1.txt",
                fileContext + "/able/to/read/secondfile1.pdf",
                fileContext + "/able/to/read/subdir/file2.txt",
                fileContext + "/able/to/read/subdir1/file3.txt"
        );

        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", null,pathsWrite,pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenReturn(Mono.just(filesInS3));

        final List<String> listOfNames = useCase.getFileNames("task_0", pathsReadonly.stream().findFirst().orElseThrow());

        assertThat(listOfNames)
                .hasSize(2)
                .contains("file1.txt", "secondfile1.pdf")
                .doesNotContain("file2.txt", "file3.txt");

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getFileNamesThrowsException() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", "syncConfig",pathsWrite,pathsReadonly));
        when(documentStorageFolderRepository.getAllFilesInFolderRecursively(anyString())).thenThrow(new DocumentStorageException("DocumentStorageException",new Exception()));

        assertThatThrownBy(() -> useCase.getFileNames("task_0","able/to/read"))
                .isInstanceOf(HttpServerErrorException.class)
                .hasMessage("500 Getting all files of folder able/to/read failed")
                .has(new Condition<>(e -> ((HttpServerErrorException) e).getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR), "Status code is 500"));

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively(anyString(),anyString());
        verifyNoMoreInteractions(documentStorageFolderRepository);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForGETWithReadAccessAndDocumentStorageUrl() {
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", "syncConfig",pathsWrite,pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(),anyString(),anyInt(),any())).thenReturn("With DocumentStorageUrl");
        when(presignedUrlPort.getPresignedUrl(anyString(),anyInt(),any())).thenReturn("Without DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.GET,"task_0","able/to/read","filename1.txt");

        assertThat(presignedUrl).isEqualTo("With DocumentStorageUrl");

        verify(presignedUrlPort).getPresignedUrl(anyString(),anyString(),anyInt(),any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForGETWithWriteAccessAndWithoutDocumentStorageUrl() {
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", null,pathsWrite,pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(),anyString(),anyInt(),any())).thenReturn("With DocumentStorageUrl");
        when(presignedUrlPort.getPresignedUrl(anyString(),anyInt(),any())).thenReturn("Without DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.GET,"task_0","able/to/write","filename1.txt");

        assertThat(presignedUrl).isEqualTo("Without DocumentStorageUrl");

        verify(presignedUrlPort).getPresignedUrl(anyString(),anyInt(),any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForPUTWithWriteAccess() {
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", "syncConfig",pathsWrite,pathsReadonly));
        when(presignedUrlPort.getPresignedUrl(anyString(),anyString(),anyInt(),any())).thenReturn("With DocumentStorageUrl");

        String presignedUrl = useCase.getPresignedUrl(PresignedUrlAction.PUT,"task_0","able/to/write","filename1.txt");

        assertThat(presignedUrl).isEqualTo("With DocumentStorageUrl");

        verify(presignedUrlPort).getPresignedUrl(anyString(),anyString(),anyInt(),any());
        verifyNoMoreInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }

    @Test
    void getPresignedUrlForPUTWithReadAccess() {
        when(taskFileConfigResolverPort.apply(any())).thenReturn(new TaskFileConfig(this.fileContext, "asyncConfig", "syncConfig",pathsWrite,List.of("readonly/path")));
        when(presignedUrlPort.getPresignedUrl(anyString(),anyString(),anyInt(),any())).thenReturn("With DocumentStorageUrl");

        assertThatThrownBy(() -> useCase.getPresignedUrl(PresignedUrlAction.PUT,"task_0","readonly/path","filename1.txt"))
                .isInstanceOf(IllegalResourceAccessException.class)
                .hasMessage("No access to defined property");

        verifyNoInteractions(presignedUrlPort);

        verify(taskFileConfigResolverPort).apply(any());
        verifyNoMoreInteractions(taskFileConfigResolverPort);
    }
}
