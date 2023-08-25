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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JobRepository {

    private final JobOperationsApi jobOperationsApi;
    private final JobAttributeOperationsApi jobAttributeOperationsApi;
    private final FileStatusOperationsApi fileStatusOperationsApi;

    /**
     * Creates a {@link Jobs}.
     * <p>
     * For params and return value see {@link JobOperationsApi#postUsingPOST5}.
     *
     * @throws OceCosmosIntegrationClientErrorException if the problem is with the client.
     * @throws OceCosmosIntegrationServerErrorException if the problem is with oce-cosmos service.
     * @throws OceCosmosIntegrationException            if the problem cannot be assigned directly to oce-cosmos service or client.
     */
    public Jobs createJob(final Jobs body) throws OceCosmosIntegrationClientErrorException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException {
        return catchErrors(() -> this.jobOperationsApi.postUsingPOST5(body), "create a job");
    }

    /**
     * Queries all {@link Jobs} for given Job-Prototyp with same layout and delivery.
     * Applies only to open and "SAMMELJOB"-Jobs.
     * <p>
     * For params and return value see {@link JobOperationsApi#getUsingGET7}.
     *
     * @throws OceCosmosIntegrationClientErrorException if the problem is with the client.
     * @throws OceCosmosIntegrationServerErrorException if the problem is with oce-cosmos service.
     * @throws OceCosmosIntegrationException            if the problem cannot be assigned directly to oce-cosmos service or client.
     */
    public List<Jobs> queryJobs(final Jobs prototype) throws OceCosmosIntegrationClientErrorException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException {

        String search = String.format("status==0 and customField==SAMMELJOB and customField1==%s and customField2==%s",
                prototype.getCustomField1(), prototype.getCustomField2());
        return catchErrors(() -> this.jobOperationsApi.getUsingGET7(null, null, null, search), "query for jobs");
    }

    /**
     * Add {@link JobAttribute}s to a given {@link Jobs}.
     * {@link JobAttribute}s are meta-data for all print jobs within a {@link Jobs}-Pool.
     * <p>
     * For params and return value see {@link JobAttributeOperationsApi#putUsingPUT2}.
     *
     * @throws OceCosmosIntegrationClientErrorException if the problem is with the client.
     * @throws OceCosmosIntegrationServerErrorException if the problem is with oce-cosmos service.
     * @throws OceCosmosIntegrationException            if the problem cannot be assigned directly to oce-cosmos service or client.
     */
    public void addJobAttributesToJob(final List<JobAttribute> attributes, Long id) throws OceCosmosIntegrationClientErrorException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException {

        catchErrors(() -> {
            this.jobAttributeOperationsApi.putUsingPUT2(attributes, id);
            return true; //Method needs to return something
        }, "add Jobattributes to a Job");
    }

    /**
     * Add {@link Filestatus}s and a File to a given {@link Jobs}.
     * Processed along all other Files within a {@link Jobs}-Pool.
     * <p>
     * For params and return value see {@link FileStatusOperationsApi#postUsingPOST2}.
     *
     * @throws OceCosmosIntegrationClientErrorException if the problem is with the client.
     * @throws OceCosmosIntegrationServerErrorException if the problem is with oce-cosmos service.
     * @throws OceCosmosIntegrationException            if the problem cannot be assigned directly to oce-cosmos service or client.
     */
    public void addDataToJob(final Filestatus filestatus, final Path file, Long id) throws OceCosmosIntegrationClientErrorException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException {

        catchErrors(() -> {
            this.fileStatusOperationsApi.postUsingPOST2(id, file, filestatus);
            return true; //Method needs to return something
        }, "add Data to a Job");
    }

    private <T> T catchErrors(Callable<T> apiCall, String actionName) throws OceCosmosIntegrationClientErrorException, OceCosmosIntegrationServerErrorException, OceCosmosIntegrationException {
        try {
            return apiCall.call();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to %s failed with %s.", actionName, exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OceCosmosIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to %s failed with %s.", actionName, exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OceCosmosIntegrationServerErrorException(message, exception);
        } catch (final Exception exception) {
            final String message = String.format("The request to %s failed.", actionName);
            log.error(exception.getMessage());
            log.error(message);
            throw new OceCosmosIntegrationException(message, exception);
        }
    }

}
