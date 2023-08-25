package io.muenchendigital.digiwf.okverkehr.integration.repository;

import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationClientErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationServerErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.gen.api.BusinessActionskfzhalterpersonenApi;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OkVerkehrHalterRepository {

    private final BusinessActionskfzhalterpersonenApi businessActionskfzhalterpersonenApi;


    /**
     * Gets a {@link HalterPersonAntwort} by {@link HalterPersonAnfrage}.
     *
     * @param request the {@link HalterPersonAnfrage}
     * @return the {@link HalterPersonAntwort} identified by the request body.
     */
    public HalterPersonAntwort getHalter(final HalterPersonAnfrage request) throws OkVerkehrIntegrationClientErrorException, OkVerkehrIntegrationServerErrorException, OkVerkehrIntegrationException {
        try {
            return this.businessActionskfzhalterpersonenApi.route1(request);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to get a halter failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkVerkehrIntegrationClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to get a halter failed with %s.", exception.getStatusCode());
            log.error(exception.getMessage());
            log.error(message);
            throw new OkVerkehrIntegrationServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = "The request to get a halter failed.";
            log.error(exception.getMessage());
            log.error(message);
            throw new OkVerkehrIntegrationException(message, exception);
        }
    }

}
