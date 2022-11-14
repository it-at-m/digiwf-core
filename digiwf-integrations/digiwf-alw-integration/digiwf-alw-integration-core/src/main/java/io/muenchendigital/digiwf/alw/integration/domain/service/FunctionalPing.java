/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package io.muenchendigital.digiwf.alw.integration.domain.service;

import io.muenchendigital.digiwf.alw.integration.domain.model.AlwPersoneninfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;

/**
 * Tests the connection to the integrated remote system.
 */
@Component
@Slf4j
@ConditionalOnProperty(
        value = "digiwf.alw.personeninfo.functional-ping.enabled",
        havingValue = "true")
@RequiredArgsConstructor
public class FunctionalPing {

    @Value("${digiwf.alw.personeninfo.functional-ping.azr-number:#{null}}")
    private String azrNumber;

    private final AlwPersoneninfoService alwPersoneninfoService;

    @PostConstruct
    public void testConnection() throws Exception {
        log.debug("Testing connection");
        try {
            alwPersoneninfoService.getZustaendigkeit(new AlwPersoneninfoRequest(azrNumber));
        } catch (Exception ex){
            if (ex.getCause() != null && ex.getCause() instanceof HttpClientErrorException){
                HttpClientErrorException cause = (HttpClientErrorException) ex.getCause();
                if (HttpStatus.NOT_FOUND.value() == cause.getRawStatusCode()){
                    log.info("Ping successful");
                    return;
                }
            }
            log.error("Functional ping failed", ex);
            throw ex;
        }
    }

}
