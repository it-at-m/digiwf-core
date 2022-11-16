/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validates the incoming azrNumber.
 */
@Component
@Slf4j
public class AzrNumberValidator {

    private static final Pattern alwNumberPattern = Pattern.compile("\\d{12}");

    public void validate(final String azrNumber) {
        // put  azr number in property to make it available for errorhandling
        log.debug("Received azrNumber: {}", azrNumber);
        if (StringUtils.isEmpty(azrNumber) || !alwNumberPattern.matcher(azrNumber).matches()) {
            throw new IllegalArgumentException("AZR-Nummer " + azrNumber + " is not valid");
        }
    }


}
