package de.muenchen.oss.digiwf.ocecosmos.integration.service;

import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationClientErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationException;
import de.muenchen.oss.digiwf.ocecosmos.integration.exception.OceCosmosIntegrationServerErrorException;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Jobs;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.JobRequest;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.response.JobResponse;
import de.muenchen.oss.digiwf.ocecosmos.integration.repository.JobRepository;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper.JobRequestMapper;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper.JobResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {

    private final JobRequestMapper jobRequestMapper;
    private final JobResponseMapper jobResponseMapper;
    private final JobRepository jobRepository;

    /**
     * Submit given Job with all attributes and File to OceCosmos.
     *
     * Creates a new Job if given Job doesn't exist.
     *
     * @param job with all job-attributes and file
     * @return job-response with metadata from corresponding job, like jobId
     * @throws OceCosmosIntegrationClientErrorException if the problem is with the client.
     * @throws OceCosmosIntegrationServerErrorException if the problem is with oce-cosmos service.
     * @throws OceCosmosIntegrationException            if the problem cannot be assigned directly to oce-cosmos service or client.
     */
    public JobResponse submitJob(final JobRequest job) throws OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException, OceCosmosIntegrationClientErrorException {

        Jobs requestedJob = jobRequestMapper.mapToJobs(job);

        JobResponse jobResponse;
        List<Jobs> existingJobs = this.jobRepository.queryJobs(requestedJob);
        if(existingJobs.isEmpty()) {
            jobResponse = jobResponseMapper.map(jobRepository.createJob(requestedJob));
            jobRepository.addJobAttributesToJob(
                    jobRequestMapper.mapToJobAttributes(job, jobResponse.getJobId()),
                    jobResponse.getJobId());

        } else if(existingJobs.size() == 1) {
            jobResponse = jobResponseMapper.map(existingJobs.get(0));

        } else {
            throw new OceCosmosIntegrationServerErrorException("Requested job exists more than once");
        }

        jobRepository.addDataToJob(jobRequestMapper.mapToFileStatus(job), job.getFile(), jobResponse.getJobId());

        return jobResponse;
    }
    
}
