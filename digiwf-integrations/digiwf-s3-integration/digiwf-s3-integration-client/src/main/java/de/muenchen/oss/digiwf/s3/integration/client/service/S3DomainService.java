package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static de.muenchen.oss.digiwf.process.api.config.ProcessConfigConstants.APP_FILE_S3_SYNC_CONFIG;

@RequiredArgsConstructor
@Slf4j
public class S3DomainService {

    private final ProcessConfigApi processConfigApi;

    private final String defaultDocumentStorageUrl;

    public Optional<String> getDomainSpecificS3Storage(final String processDefinition) {
        return processConfigApi.getProcessConfigValue(APP_FILE_S3_SYNC_CONFIG, processDefinition);
    }

    public String getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        if (StringUtils.isNotBlank(this.defaultDocumentStorageUrl)) {
            return this.defaultDocumentStorageUrl;
        }
        final String message = "Default document storage is not set. Make sure the property io.muenchendigital.digiwf.s3.client.documentStorageUrl is set.";
        log.error(message);
        throw new PropertyNotSetException(message);
    }
}
