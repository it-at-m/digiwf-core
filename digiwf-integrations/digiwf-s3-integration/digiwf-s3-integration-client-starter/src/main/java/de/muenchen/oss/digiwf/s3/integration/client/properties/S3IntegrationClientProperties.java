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
@ConfigurationProperties(prefix = "digiwf.s3.client")
public class S3IntegrationClientProperties {

    private String documentStorageUrl;
    private boolean enableSecurity;

    /** Maximum allowed file size. Default is 0, which indicates that there is no limit. */
    private DataSize maxFileSize = DataSize.ofBytes(0L);
    /** Maximum allowed size of a batch of files. Default is 0, which indicates that there is no limit. */
    private DataSize maxBatchSize = DataSize.ofBytes(0L);
    /** Supported file extensions. */
    private Map<String, String> supportedFileExtensions;

}
