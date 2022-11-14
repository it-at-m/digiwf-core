package io.muenchendigital.digiwf.alw.integration.api.controller;

import io.muenchendigital.digiwf.alw.integration.domain.exception.AlwException;
import io.muenchendigital.digiwf.alw.integration.domain.model.AlwPersoneninfoRequest;
import io.muenchendigital.digiwf.alw.integration.domain.model.AlwPersoneninfoResponse;
import io.muenchendigital.digiwf.alw.integration.domain.service.AlwPersoneninfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controller as an api for the {@link AlwPersoneninfoService } without streaming infrastructure.
 */
@RestController
@Profile({"local", "test"})
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final AlwPersoneninfoService alwPersoneninfoService;

    @GetMapping(value = "/getZustaendigkeit")
    public String getZustaendigkeit(@RequestParam final String azrNumber) throws AlwException {
        log.debug("Incoming request");
        AlwPersoneninfoResponse response;
        try {
            response = alwPersoneninfoService.getZustaendigkeit(new AlwPersoneninfoRequest(azrNumber));
        } catch (final Exception ex){
            if (ex.getCause() != null && ex.getCause() instanceof HttpClientErrorException){
                final HttpClientErrorException cause = (HttpClientErrorException) ex.getCause();
                if (HttpStatus.NOT_FOUND.value() == cause.getRawStatusCode()){
                    log.info("Connection successful");
                    return "Not found";
                }
            }
            log.error("Request failed", ex);
            throw ex;
        }
        log.info("Request successful");
        return response.getZustaendigeGruppe();
    }

}
