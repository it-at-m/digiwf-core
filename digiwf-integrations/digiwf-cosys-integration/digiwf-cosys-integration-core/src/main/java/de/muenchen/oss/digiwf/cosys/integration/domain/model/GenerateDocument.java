/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.cosys.integration.domain.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    protected String client;

    /**
     * Role that is used in cosys
     */
    @NotBlank(message = "role is mandatory")
    protected String role;

    /**
     * The GUID of the target template to be filled
     */
    @NotBlank(message = "guid is mandatory")
    protected String guid;

    /**
     * All data to be filled into template
     */
    protected JsonNode variables;
}
