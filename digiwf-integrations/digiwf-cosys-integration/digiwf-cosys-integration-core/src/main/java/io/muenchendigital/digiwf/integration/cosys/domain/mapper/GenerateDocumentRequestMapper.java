/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.integration.cosys.domain.mapper;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.integration.cosys.configuration.CosysConfiguration;
import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Map the input model for the generatedocument interface
 * to the input model of cosys including xml-building and validating.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateDocumentRequestMapper {

    private final CosysConfiguration configuration;

    public GenerateDocumentRequest map(final GenerateDocument model) throws IOException {
        final GenerateDocumentRequest request = new GenerateDocumentRequest();
        request.setClient(model.getClient());
        request.setData(new Gson().toJson(model.getVariables()).getBytes(StandardCharsets.UTF_8));
        request.setGuid(model.getGuid());
        request.setMerge(this.configuration.getMergeOptions());
        request.setRole(model.getRole());

        return request;
    }


}
