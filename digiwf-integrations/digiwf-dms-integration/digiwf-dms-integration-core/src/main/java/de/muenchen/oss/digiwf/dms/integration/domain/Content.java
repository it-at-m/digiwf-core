package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {

    private String extension;
    private String name;
    private byte[] content;

}
