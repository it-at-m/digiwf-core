package io.muenchendigital.digiwf.s3.integration.client.service;

import io.muenchendigital.digiwf.s3.integration.client.exception.PropertyNotSetException;
import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;
import io.muenchendigital.digiwf.s3.integration.gen.api.FileApiApi;
import io.muenchendigital.digiwf.s3.integration.gen.api.FolderApiApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class ApiClientFactory {

    private final String defaultDocumentStorageUrl;

    private final RestTemplate restTemplate;

    public String getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        if (StringUtils.isNotBlank(this.defaultDocumentStorageUrl)) {
            return this.defaultDocumentStorageUrl;
        }
        final String message = "The property io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl is not set.";
        log.error(message);
        throw new PropertyNotSetException(message);
    }

    public FileApiApi getFileApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FileApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    public FolderApiApi getFolderApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FolderApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    private ApiClient getApiClientForDocumentStorageUrl(final String documentStorageUrl) {
        final ApiClient apiClient = new ApiClient(this.restTemplate);
        apiClient.setBasePath(documentStorageUrl);
        return apiClient;
    }

}
