package de.muenchen.oss.digiwf.s3.integration.example.client.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileActionDto {
    private String action;
    private String presignedUrl;
    private String file;
}
