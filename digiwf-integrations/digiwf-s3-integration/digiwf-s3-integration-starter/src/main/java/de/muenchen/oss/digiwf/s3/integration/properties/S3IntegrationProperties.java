package de.muenchen.oss.digiwf.s3.integration.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "digiwf.s3")
public class S3IntegrationProperties {

    @NotBlank
    private String url;

    @NotBlank
    private String accessKey;

    @NotBlank
    private String secretKey;

    @NotBlank
    private String bucketName;

    private Boolean initialConnectionTest;

    private int presignedUrlExpiresInMinutes = 10080; // 7 days

    private Boolean proxyEnabled = false;

    private String proxyUrl;

}
