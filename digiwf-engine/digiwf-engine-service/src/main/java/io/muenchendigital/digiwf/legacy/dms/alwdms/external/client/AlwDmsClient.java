/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.alwdms.external.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.muenchendigital.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import io.muenchendigital.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.alwdms.external.mapper.ReadMetadataTOMapper;
import io.muenchendigital.digiwf.legacy.dms.alwdms.external.mapper.ReadSchrifstueckTOMapper;
import io.muenchendigital.digiwf.legacy.dms.alwdms.external.transport.ReadMetadataTO;
import io.muenchendigital.digiwf.legacy.dms.alwdms.external.transport.ReadSchriftstueckTO;
import io.muenchendigital.digiwf.legacy.dms.alwdms.properties.AlwDmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;

/**
 * Rest-Client for Alw-Dms-EAI.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AlwDmsClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AlwDmsProperties properties;

    private final ReadSchrifstueckTOMapper readSchrifstueckMapper;
    private final ReadMetadataTOMapper readMetadataMapper;

    /**
     * Load the document.
     *
     * @param username         user to process the request
     * @param schriftstueckCOO COO of the document
     * @return the document
     * @throws JsonProcessingException
     */
    public AlwSchriftstueck readSchriftstueck(@NotBlank final String username, @NotBlank final String schriftstueckCOO) throws JsonProcessingException {
        final String restCallFullUrl = this.properties.getEaiUrl() + "camel/schriftstueck?login=" + username + "&schriftstueckCOO=" + schriftstueckCOO;
        log.info("calling readSchriftstueck: {}", schriftstueckCOO);
        final String resultJSON = this.restTemplate.getForObject(restCallFullUrl, String.class);
        val schritstueck = new ObjectMapper().readValue(resultJSON, ReadSchriftstueckTO.class);
        return this.readSchrifstueckMapper.map(schritstueck);
    }

    /**
     * Load metadata.
     *
     * @param userLogin user to process the request
     * @param objCOO    COO of the object
     * @return the metadata
     * @throws JsonProcessingException
     */
    public AlwMetadata readContentMetaData(@NotBlank final String userLogin, @NotBlank final String objCOO)
            throws JsonProcessingException {
        final String restCallFullUrl = this.properties.getEaiUrl() + "camel/metadata?login=" + userLogin + "&objCOO=" + objCOO;
        log.info("calling readContentMetaData: {}", objCOO);
        final String resultJSON = this.restTemplate.getForObject(restCallFullUrl, String.class);

        val metadata = new ObjectMapper().readValue(resultJSON, ReadMetadataTO.class);
        return this.readMetadataMapper.map(metadata);
    }

}
