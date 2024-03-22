package de.muenchen.oss.digiwf.ticket.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileContent {
    private String mimeType;
    private String name;
    private byte[] data;

}
