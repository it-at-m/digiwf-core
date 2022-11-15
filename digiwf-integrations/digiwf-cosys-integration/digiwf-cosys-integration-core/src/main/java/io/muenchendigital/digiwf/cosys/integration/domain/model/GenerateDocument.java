/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.cosys.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> variables;

    /**
     * A list of presigned urls that are used to save the cosys documents in a s3 storage
     */
    @Valid
    @Size(min = 1, max = 1)
    private List<DocumentStorageUrl> documentStorageUrls;
}
