package de.muenchen.oss.digiwf.s3.integration.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePresignedUrlEvent {

    @Pattern(regexp = "GET|PUT|DELETE|POST")
    private String action;
    @NotNull(message = "path is mandatory")
    @NotBlank(message = "path is mandatory")
    private String path;

}
