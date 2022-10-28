package io.muenchendigital.digiwf.integration.cosys.domain.model;

import lombok.Data;

/**
 * Request parameters for external generatedocument interface.
 *
 * @author externer.dl.horn
 */
@Data
public class GenerateDocumentRequest {

    private String guid;
    private String client;
    private String role;
    private String stateFilter;
    private String validity;

    private byte[] data;
    private byte[] merge;
}
