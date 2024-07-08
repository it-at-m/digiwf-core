package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "File paths to sizes")
public class FileSizesInFolderDto {

    private Map<String, Long> fileSizes;
}
