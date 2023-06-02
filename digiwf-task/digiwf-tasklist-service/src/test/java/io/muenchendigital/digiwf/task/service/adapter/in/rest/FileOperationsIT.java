package io.muenchendigital.digiwf.task.service.adapter.in.rest;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.jpa.JpaPolyflowViewTaskService;
import io.holunda.polyflow.view.query.task.AllTasksQuery;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import io.muenchendigital.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import io.muenchendigital.digiwf.task.service.TaskListApplication;
import io.muenchendigital.digiwf.task.service.infra.file.S3MockConfiguration;
import io.muenchendigital.digiwf.task.service.infra.security.TestUser;
import io.muenchendigital.digiwf.task.service.infra.security.WithKeycloakUser;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import static io.muenchendigital.digiwf.task.TaskVariables.*;
import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.BASE_PATH;
import static io.muenchendigital.digiwf.task.service.adapter.in.rest.RestConstants.SERVLET_PATH;
import static io.muenchendigital.digiwf.task.service.application.usecase.TestFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = TaskListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {S3MockConfiguration.class})
@ActiveProfiles({"itest", "embedded-kafka"})
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(
        partitions = 1,
        topics = {"plf_data_entries", "plf_tasks"}
)
@Slf4j
@WireMockTest(httpPort = 7080)
@DirtiesContext
public class FileOperationsIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaPolyflowViewTaskService service;

    @Autowired
    private PresignedUrlRepository presignedUrlRepository;

    @Autowired
    DocumentStorageFolderRepository documentStorageFolderRepository;

    Task task = generateTask("task_0", Sets.newHashSet(), Sets.newHashSet(), TestUser.USER_ID, null, true,
            CamundaBpmData
                    .builder()
                    .set(PROCESS_FILE_CONTEXT, "FileContext")
                    .set(PROCESS_ASYNC_CONFIG, "AsyncConfig")
                    .set(PROCESS_SYNC_CONFIG, "SyncConfig")
                    .set(FILE_PATHS, "File;paths")
                    .set(FILE_PATHS_READONLY, "File;paths;read;only")
                    .build()
    );

    @BeforeEach
    public void produce_task_event() {
        service.on(createEvent(task), MetaData.emptyInstance());
        await().untilAsserted(
                () -> {
                    var count = service.query(new AllTasksQuery()).getTotalElementCount();
                    assertThat(count).isEqualTo(1);
                }
        );

    }

    @AfterEach
    public void clean_tasks() {
        service.on(deleteEvent(task), MetaData.emptyInstance());
    }

    @Test
    @WithKeycloakUser
    public void get_filenames() throws Exception {

        when(documentStorageFolderRepository.getAllFilesInFolderRecursively("FileContext/only","SyncConfig")).thenReturn(Mono.just(Sets.newHashSet("able/to/read/file1.txt","able/to/read/file2.pdf")));

        mockMvc
            .perform(
                    get(BASE_PATH + "/tasks/id/task_0/file")
                            .param("filePath", "only")
                            .servletPath(SERVLET_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").value("file2.pdf"))
            .andExpect(jsonPath("$[1]").value("file1.txt"));

        verify(documentStorageFolderRepository).getAllFilesInFolderRecursively("FileContext/only","SyncConfig");
        verifyNoMoreInteractions(documentStorageFolderRepository);


    }

    @Test
    @WithKeycloakUser
    public void get_presignedUrl_for_download_file() throws  Exception{
        when(presignedUrlRepository.getPresignedUrlGetFile("FileContext/read/filenameGet", 5, "SyncConfig")).thenReturn(Mono.just("Presigned URL for download"));

        mockMvc
            .perform(
                get(BASE_PATH + "/tasks/id/task_0/file/filenameGet")
                    .param("filePath", "read")
                    .param("requestMethod", "GET")
                    .servletPath(SERVLET_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Presigned URL for download"));

        verify(presignedUrlRepository).getPresignedUrlGetFile("FileContext/read/filenameGet", 5, "SyncConfig");
        verifyNoMoreInteractions(presignedUrlRepository);
    }

    @Test
    @WithKeycloakUser
    public void get_presignedUrl_for_upload_file() throws  Exception{
        when(presignedUrlRepository.getPresignedUrlSaveFile("FileContext/paths/filenamePost", 5, null, "SyncConfig")).thenReturn("Presigned URL for upload");

        mockMvc
            .perform(
                get(BASE_PATH + "/tasks/id/task_0/file/filenamePost")
                    .param("filePath", "paths")
                    .param("requestMethod", "POST")
                    .servletPath(SERVLET_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Presigned URL for upload"));

        verify(presignedUrlRepository).getPresignedUrlSaveFile("FileContext/paths/filenamePost", 5, null, "SyncConfig");
        verifyNoMoreInteractions(presignedUrlRepository);
    }

    @Test
    @WithKeycloakUser
    public void get_presignedUrl_for_delete_file() throws  Exception{
        when(presignedUrlRepository.getPresignedUrlDeleteFile("FileContext/File/filenameDelete", 5, "SyncConfig")).thenReturn("Presigned URL for delete");

        mockMvc
            .perform(
                get(BASE_PATH + "/tasks/id/task_0/file/filenameDelete")
                    .param("filePath", "File")
                    .param("requestMethod", "DELETE")
                    .servletPath(SERVLET_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("Presigned URL for delete"));

        verify(presignedUrlRepository).getPresignedUrlDeleteFile("FileContext/File/filenameDelete", 5, "SyncConfig");
        verifyNoMoreInteractions(presignedUrlRepository);

    }

}
