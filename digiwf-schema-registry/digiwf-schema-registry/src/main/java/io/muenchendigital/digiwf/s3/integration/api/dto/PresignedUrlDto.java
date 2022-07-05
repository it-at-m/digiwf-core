package io.muenchendigital.digiwf.s3.integration.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Presigned file url")
public class PresignedUrlDto {

    private String url;

}
