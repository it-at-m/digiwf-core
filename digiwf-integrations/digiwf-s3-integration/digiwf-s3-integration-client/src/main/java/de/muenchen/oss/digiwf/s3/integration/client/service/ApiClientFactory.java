package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.ApiClient;
import de.muenchen.oss.digiwf.s3.integration.client.api.FileApiApi;
import de.muenchen.oss.digiwf.s3.integration.client.api.FolderApiApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Slf4j
public class ApiClientFactory {

    private final WebClient webClient;

    public FileApiApi getFileApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FileApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    public FolderApiApi getFolderApiForDocumentStorageUrl(final String documentStorageUrl) {
        return new FolderApiApi(this.getApiClientForDocumentStorageUrl(documentStorageUrl));
    }

    private ApiClient getApiClientForDocumentStorageUrl(final String documentStorageUrl) {
        final ApiClient apiClient = new ApiClient(this.webClient);
        apiClient.setBasePath(documentStorageUrl);
        return apiClient;
    }

}
