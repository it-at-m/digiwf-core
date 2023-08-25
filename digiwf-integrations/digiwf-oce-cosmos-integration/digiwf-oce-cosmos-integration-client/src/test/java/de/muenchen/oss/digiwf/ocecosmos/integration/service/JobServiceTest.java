package de.muenchen.oss.digiwf.ocecosmos.integration.service;

import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationClientErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationServerErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.repository.JobRepository;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Jobs;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper.JobRequestMapper;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper.JobResponseMapper;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DataTypes;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DeliveryTypes;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.JobRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;
    @Spy
    private JobRequestMapper jobRequestMapper = Mappers.getMapper(JobRequestMapper.class);
    @Spy
    private JobResponseMapper jobResponseMapper = Mappers.getMapper(JobResponseMapper.class);

    private JobService jobService;

    @BeforeEach
    public void beforeEach() {
        this.jobService = new JobService(jobRequestMapper, jobResponseMapper, jobRepository);
    }

    @Test
    public void submitNewJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        var jobrequest = getPrefilledJobRequest();

        Mockito.when(this.jobRepository.queryJobs(any())).thenReturn(Collections.emptyList());
        Mockito.when(this.jobRepository.createJob(any())).thenReturn(getJobsWithId(123L));

        var response = jobService.submitJob(jobrequest);

        assertThat(response.getJobId(), is(123L));

        Mockito.verify(this.jobRepository, Mockito.times(1)).queryJobs(any());
        Mockito.verify(this.jobRepository, Mockito.times(1)).createJob(any());
        Mockito.verify(this.jobRepository, Mockito.times(1)).addJobAttributesToJob(any(), eq(123L));
        Mockito.verify(this.jobRepository, Mockito.times(1)).addDataToJob(any(), any(), eq(123L));
    }

    @Test
    public void submitExistingJob() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        var jobrequest = getPrefilledJobRequest();

        Mockito.when(this.jobRepository.queryJobs(any())).thenReturn(Collections.singletonList(getJobsWithId(123L)));

        var response = jobService.submitJob(jobrequest);

        assertThat(response.getJobId(), is(123L));

        Mockito.verify(this.jobRepository, Mockito.times(1)).queryJobs(any());
        Mockito.verify(this.jobRepository, Mockito.times(0)).createJob(any());
        Mockito.verify(this.jobRepository, Mockito.times(0)).addJobAttributesToJob(any(), any());
        Mockito.verify(this.jobRepository, Mockito.times(1)).addDataToJob(any(), any(), eq(123L));
    }

    @Test
    public void submitExistingMultiJobWithError() throws OceCosmosIntegrationException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationClientErrorException {
        var jobrequest = getPrefilledJobRequest();

        Mockito.when(this.jobRepository.queryJobs(any())).thenReturn(Arrays.asList(
                getJobsWithId(321L),
                getJobsWithId(123L)));

        Exception exception = assertThrows(OceCosmosIntegrationServerErrorException.class,
                () -> jobService.submitJob(jobrequest));

        assertThat(exception.getMessage(), is("Requested job exists more than once"));

        Mockito.verify(this.jobRepository, Mockito.times(1)).queryJobs(any());
        Mockito.verify(this.jobRepository, Mockito.times(0)).createJob(any());
        Mockito.verify(this.jobRepository, Mockito.times(0)).addJobAttributesToJob(any(), any());
        Mockito.verify(this.jobRepository, Mockito.times(0)).addDataToJob(any(), any(), any());
    }

    private JobRequest getPrefilledJobRequest() {
        return JobRequest.builder()
                .clientId("client")
                .file(Path.of("tmp/test.json"))
                .printJob(true)
                .applicationName("test-app")
                .dataType(DataTypes.JSON)
                .debtor("10101")
                .deliveryType(DeliveryTypes.POSTVERSAND)
                .templateName("Template1")
                .jobType("SAMMELJOB")
                .build();
    }

    private Jobs getJobsWithId(Long id) {
        var jobs = new Jobs();
        ReflectionTestUtils.setField(jobs, "id", id);
        return jobs;
    }
}