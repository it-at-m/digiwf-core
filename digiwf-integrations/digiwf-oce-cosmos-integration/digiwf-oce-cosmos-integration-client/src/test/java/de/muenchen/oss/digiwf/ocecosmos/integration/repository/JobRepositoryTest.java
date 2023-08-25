package de.muenchen.oss.digiwf.ocecosmos.integration.repository;

import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationClientErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationServerErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.FileStatusOperationsApi;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.JobAttributeOperationsApi;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.JobOperationsApi;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Filestatus;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.JobAttribute;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Jobs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JobRepositoryTest {

    @Mock
    private JobOperationsApi jobOperationsApi;
    @Mock
    private JobAttributeOperationsApi jobAttributeOperationsApi;
    @Mock
    private FileStatusOperationsApi fileStatusOperationsApi;

    private JobRepository jobRepository;

    @BeforeEach
    public void beforeEach() {
        this.jobRepository = new JobRepository(this.jobOperationsApi, this.jobAttributeOperationsApi, this.fileStatusOperationsApi);
        Mockito.reset(this.jobOperationsApi, this.jobAttributeOperationsApi, this.fileStatusOperationsApi);
    }

    @Test
    void createJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var jobRequest = getPrefilledJob();
        final var jobReturn = getPrefilledJob().parentId(123L);

        Mockito.when(this.jobOperationsApi.postUsingPOST5(jobRequest)).thenReturn(jobReturn);

        final var result = this.jobRepository.createJob(jobRequest);

        assertThat(result.getParentId(), is(123L));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).postUsingPOST5(jobRequest);
    }

    @Test
    void createJobClientException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.postUsingPOST5(jobRequest)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        Exception exception = assertThrows(OceCosmosIntegrationClientErrorException.class,
                () -> this.jobRepository.createJob(jobRequest));

        assertThat(exception.getMessage(), is("The request to create a job failed with 400 BAD_REQUEST."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).postUsingPOST5(jobRequest);
    }

    @Test
    void createJobServerException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.postUsingPOST5(jobRequest)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Exception exception = assertThrows(OceCosmosIntegrationServerErrorException.class,
                () -> this.jobRepository.createJob(jobRequest));

        assertThat(exception.getMessage(), is("The request to create a job failed with 500 INTERNAL_SERVER_ERROR."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).postUsingPOST5(jobRequest);
    }

    @Test
    void createJobUnknownException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.postUsingPOST5(jobRequest)).thenThrow(new RuntimeException("Look at me, I'm an exception"));

        Exception exception = assertThrows(OceCosmosIntegrationException.class,
                () -> this.jobRepository.createJob(jobRequest));

        assertThat(exception.getMessage(), is("The request to create a job failed."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).postUsingPOST5(jobRequest);
    }

    @Test
    void queryEmptyJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var jobRequest = getPrefilledJob();
        final var jobsReturn = Collections.<Jobs>emptyList();

        String search = String.format("status==0 and customField==SAMMELJOB and customField1==%s and customField2==%s",
                jobRequest.getCustomField1(), jobRequest.getCustomField2());
        Mockito.when(this.jobOperationsApi.getUsingGET7(null, null, null, search)).thenReturn(jobsReturn);

        final var result = this.jobRepository.queryJobs(jobRequest);

        assertThat(result, empty());

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(null, null, null, search);
    }

    @Test
    void queryOneJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var jobRequest = getPrefilledJob();
        final var jobsReturn = Collections.singletonList(new Jobs().parentId(123L));

        String search = String.format("status==0 and customField==SAMMELJOB and customField1==%s and customField2==%s",
                jobRequest.getCustomField1(), jobRequest.getCustomField2());
        Mockito.when(this.jobOperationsApi.getUsingGET7(null, null, null, search)).thenReturn(jobsReturn);

        final var result = this.jobRepository.queryJobs(jobRequest);

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getParentId(), is(123L));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(null, null, null, search);
    }

    @Test
    void queryJobMultipleJobs() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var jobRequest = getPrefilledJob();
        final var jobsReturn = Arrays.asList(new Jobs().parentId(123L), new Jobs().parentId(456L));

        String search = String.format("status==0 and customField==SAMMELJOB and customField1==%s and customField2==%s",
                jobRequest.getCustomField1(), jobRequest.getCustomField2());
        Mockito.when(this.jobOperationsApi.getUsingGET7(null, null, null, search)).thenReturn(jobsReturn);

        final var result = this.jobRepository.queryJobs(jobRequest);

        assertThat(result.size(), is(2));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(null, null, null, search);
    }

    @Test
    void queryJobClientException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.getUsingGET7(any(), any(), any(), any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        Exception exception = assertThrows(OceCosmosIntegrationClientErrorException.class,
                () -> this.jobRepository.queryJobs(jobRequest));

        assertThat(exception.getMessage(), is("The request to query for jobs failed with 400 BAD_REQUEST."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(any(), any(), any(), any());
    }

    @Test
    void queryJobServerException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.getUsingGET7(any(), any(), any(), any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Exception exception = assertThrows(OceCosmosIntegrationServerErrorException.class,
                () -> this.jobRepository.queryJobs(jobRequest));

        assertThat(exception.getMessage(), is("The request to query for jobs failed with 500 INTERNAL_SERVER_ERROR."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(any(), any(), any(), any());
    }

    @Test
    void queryJobUnknownException() {
        final var jobRequest = getPrefilledJob();

        Mockito.when(this.jobOperationsApi.getUsingGET7(any(), any(), any(), any())).thenThrow(new RuntimeException("Look at me, I'm an exception"));

        Exception exception = assertThrows(OceCosmosIntegrationException.class,
                () -> this.jobRepository.queryJobs(jobRequest));

        assertThat(exception.getMessage(), is("The request to query for jobs failed."));

        Mockito.verify(this.jobOperationsApi, Mockito.times(1)).getUsingGET7(any(), any(), any(), any());
    }

    @Test
    void addJobAttributesToJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var attributes = getPrefilledJobAttributes();

        this.jobRepository.addJobAttributesToJob(attributes, 123L);

        Mockito.verify(this.jobAttributeOperationsApi, Mockito.times(1)).putUsingPUT2(attributes, 123L);
    }

    @Test
    void addJobAttributesToJobClientException() {
        final var attributes = getPrefilledJobAttributes();

        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.jobAttributeOperationsApi).putUsingPUT2(any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationClientErrorException.class,
                () -> this.jobRepository.addJobAttributesToJob(attributes, 123L));

        assertThat(exception.getMessage(), is("The request to add Jobattributes to a Job failed with 400 BAD_REQUEST."));

        Mockito.verify(this.jobAttributeOperationsApi, Mockito.times(1)).putUsingPUT2(any(), any());
    }

    @Test
    void addJobAttributesToJobServerException() {
        final var attributes = getPrefilledJobAttributes();

        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.jobAttributeOperationsApi).putUsingPUT2(any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationServerErrorException.class,
                () -> this.jobRepository.addJobAttributesToJob(attributes, 123L));

        assertThat(exception.getMessage(), is("The request to add Jobattributes to a Job failed with 500 INTERNAL_SERVER_ERROR."));

        Mockito.verify(this.jobAttributeOperationsApi, Mockito.times(1)).putUsingPUT2(any(), any());
    }

    @Test
    void addJobAttributesToJobUnknownException() {
        final var attributes = getPrefilledJobAttributes();

        doThrow(new RuntimeException("Look at me, I'm an exception")).when(this.jobAttributeOperationsApi).putUsingPUT2(any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationException.class,
                () -> this.jobRepository.addJobAttributesToJob(attributes, 123L));

        assertThat(exception.getMessage(), is("The request to add Jobattributes to a Job failed."));

        Mockito.verify(this.jobAttributeOperationsApi, Mockito.times(1)).putUsingPUT2(any(), any());
    }

    @Test
    void addDataToJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        final var status = getPrefilledFilestatus();
        final var file = Path.of("tmp/test.json");

        this.jobRepository.addDataToJob(status, file, 123L);

        Mockito.verify(this.fileStatusOperationsApi, Mockito.times(1)).postUsingPOST2(123L, file, status);
    }

    @Test
    void addDataToJobClientException() {
        final var status = getPrefilledFilestatus();
        final var file = Path.of("tmp/test.json");

        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.fileStatusOperationsApi).postUsingPOST2(any(), any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationClientErrorException.class,
                () -> this.jobRepository.addDataToJob(status, file, 123L));

        assertThat(exception.getMessage(), is("The request to add Data to a Job failed with 400 BAD_REQUEST."));

        Mockito.verify(this.fileStatusOperationsApi, Mockito.times(1)).postUsingPOST2(123L, file, status);
    }

    @Test
    void addDataToJobServerException() {
        final var status = getPrefilledFilestatus();
        final var file = Path.of("tmp/test.json");

        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.fileStatusOperationsApi).postUsingPOST2(any(), any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationServerErrorException.class,
                () -> this.jobRepository.addDataToJob(status, file, 123L));

        assertThat(exception.getMessage(), is("The request to add Data to a Job failed with 500 INTERNAL_SERVER_ERROR."));

        Mockito.verify(this.fileStatusOperationsApi, Mockito.times(1)).postUsingPOST2(123L, file, status);
    }

    @Test
    void addDataToJobUnknownException() {
        final var status = getPrefilledFilestatus();
        final var file = Path.of("tmp/test.json");

        doThrow(new RuntimeException("Look at me, I'm an exception")).when(this.fileStatusOperationsApi).postUsingPOST2(any(), any(), any());

        Exception exception = assertThrows(OceCosmosIntegrationException.class,
                () -> this.jobRepository.addDataToJob(status, file, 123L));

        assertThat(exception.getMessage(), is("The request to add Data to a Job failed."));

        Mockito.verify(this.fileStatusOperationsApi, Mockito.times(1)).postUsingPOST2(123L, file, status);
    }

    private Jobs getPrefilledJob() {
        return new Jobs()
                .client("Test")
                .title("PAPI.Test.SAMMELJOB")
                .categoryName("Jobs")
                .dataType("JSON")
                .inputChannel("RESTAPI")
                .customField("SAMMELJOB")
                .customField1("Test");
    }

    private List<JobAttribute> getPrefilledJobAttributes() {
        return Arrays.asList(
                new JobAttribute().jobId(123L).attributeName("Attribute1").attributeValue("Attribute1Value"),
                new JobAttribute().jobId(123L).attributeName("Attribute2").attributeValue("Attribute2Value")
        );

    }

    private Filestatus getPrefilledFilestatus() {
        return new Filestatus()
                .jobId(123L)
                .textStatus("Hochgeladen XYZ")
                .fileName("Test.json")
                .application("OceInt")
                .fileType("INPUT")
                .dataType("JSON");
    }

}
