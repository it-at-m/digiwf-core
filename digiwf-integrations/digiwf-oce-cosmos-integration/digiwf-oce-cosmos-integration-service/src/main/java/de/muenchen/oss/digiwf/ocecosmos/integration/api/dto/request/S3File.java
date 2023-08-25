package de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class S3File {


    private String url;

    private String path;

    /**
     * Proper Http Method (Post, Put, Get, Delete) to interact with S3.
     * Note: Only GET is supported. The print integration is not intended to modify files!
     */
    private String action;

}
