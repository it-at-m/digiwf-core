package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "File path to size")
public class FileSizeDto {

    private long fileSize;
}
