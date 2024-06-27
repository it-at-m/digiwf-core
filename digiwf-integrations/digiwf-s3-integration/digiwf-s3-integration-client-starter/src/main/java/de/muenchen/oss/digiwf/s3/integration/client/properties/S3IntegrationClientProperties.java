package de.muenchen.oss.digiwf.s3.integration.client.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.s3.client")
public class S3IntegrationClientProperties {

    private String documentStorageUrl;
    private boolean enableSecurity;

    /** Maximum allowed file size. Default is 100MB. */
    private DataSize maxFileSize = DataSize.ofMegabytes(100);
    /** Maximum allowed folder size. Default is 500MB. */
    private DataSize maxFolderSize = DataSize.ofMegabytes(500);
    /**
     * Supported file extensions.
     */
    private Map<String, String> supportedFileExtensions;

}
