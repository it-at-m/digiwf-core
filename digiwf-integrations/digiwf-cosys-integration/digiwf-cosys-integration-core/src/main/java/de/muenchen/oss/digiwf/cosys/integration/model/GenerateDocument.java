/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.cosys.integration.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto for generating documents.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateDocument {


    /**
     * Client that is used in cosys
     */
    @NotBlank(message = "client is mandatory")
    private String client;

    /**
     * Role that is used in cosys
     */
    @NotBlank(message = "role is mandatory")
    private String role;

    /**
     * The GUID of the target template to be filled
     */
    @NotBlank(message = "guid is mandatory")
    private String guid;

    /**
     * All data to be filled into template
     */
    private JsonNode variables;

    /**
     * A list of presigned urls that are used to save the cosys documents in a s3 storage
     */
    @Valid
    @Size(min = 1, max = 1)
    private List<DocumentStorageUrl> documentStorageUrls;
}
