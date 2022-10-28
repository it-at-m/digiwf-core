package io.muenchendigital.digiwf.integration.cosys.domain.service;

import io.muenchendigital.digiwf.integration.cosys.configuration.CosysConfiguration;
import io.muenchendigital.digiwf.integration.cosys.configuration.RestTemplateFactory;
import io.muenchendigital.digiwf.integration.cosys.domain.mapper.GenerateDocumentRequestMapper;
import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocumentRequest;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class CosysService {

    private final RestTemplate restTemplate;
    private final CosysConfiguration cosysConfiguration;
    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final GenerateDocumentRequestMapper generateDocumentRequestMapper;


    static final String ATTRIBUTE_CLIENT = "client";
    static final String ATTRIBUTE_ROLE = "role";
    static final String ATTRIBUTE_DATA = "data";
    static final String ATTRIBUTE_MERGE = "merge";
    static final String ATTRIBUTE_STATE_FILTER = "stateFilter";
    static final String ATTRIBUTE_VALIDITY = "validity";


    public CosysService(final RestTemplateFactory restTemplateFactory, final CosysConfiguration cosysConfiguration, final DocumentStorageFileRepository documentStorageFileRepository, final GenerateDocumentRequestMapper generateDocumentRequestMapper) {
        this.restTemplate = restTemplateFactory.authenticatedRestTemplate();
        this.cosysConfiguration = cosysConfiguration;
        this.documentStorageFileRepository = documentStorageFileRepository;
        this.generateDocumentRequestMapper = generateDocumentRequestMapper;
    }

    /**
     * Generate a Document in Cosys and save it in S3 Path.
     *
     * @param generateDocument Data for generating documents
     */
    public void createDocument(final GenerateDocument generateDocument) {
        final byte[] data = this.generateDocument(generateDocument);
        this.saveDocument(generateDocument, data);
    }

    //------------------------------ helper methods ------------------------------//

    private byte[] generateDocument(final GenerateDocument generateDocument) {
        try {
            final GenerateDocumentRequest generateDocumentRequest = this.generateDocumentRequestMapper.map(generateDocument);
            final String url = this.createCosysUrl(generateDocumentRequest);
            final HttpEntity body = this.createBody(generateDocumentRequest);
            return this.restTemplate.postForObject(url, body, byte[].class);
        } catch (final Exception ex) {
            log.error("Document could not be created.", ex);
            throw new RuntimeException("Document could not be created.");
        }
    }

    private void saveDocument(final GenerateDocument generateDocument, final byte[] data) {
        try {
            this.documentStorageFileRepository.saveFile(generateDocument.getS3Path(), data, 3, null);
        } catch (final Exception ex) {
            log.error("Document could not be saved", ex);
            throw new RuntimeException("Document could not be saved.");
        }
    }

    private String createCosysUrl(final GenerateDocumentRequest request) {
        final StringBuilder url = new StringBuilder();
        url.append(this.cosysConfiguration.getUrl());
        url.append("/generation/");
        url.append(request.getGuid());
        url.append("/pdf");
        url.append("?" + ATTRIBUTE_CLIENT + "=" + request.getClient());
        url.append("&" + ATTRIBUTE_ROLE + "=" + request.getRole());

        if (!StringUtils.isEmpty(request.getStateFilter())) {
            url.append("&" + ATTRIBUTE_STATE_FILTER + "=" + request.getStateFilter());
        }

        if (!StringUtils.isEmpty(request.getStateFilter())) {
            url.append("&" + ATTRIBUTE_VALIDITY + "=" + request.getValidity());
        }

        url.append("&bridgeEntpoint=true&throwExceptionOnFailure=true&connectTimeout=1500");

        return url.toString();
    }

    private HttpEntity createBody(final GenerateDocumentRequest request) throws IOException {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setConnection("keep-alive");
        headers.setAccept(List.of(MediaType.MULTIPART_FORM_DATA));

        final MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();


        body.set(ATTRIBUTE_DATA, this.createResource(ATTRIBUTE_DATA, ".xml", request.getData()));
        body.set(ATTRIBUTE_MERGE, this.createResource(ATTRIBUTE_MERGE, ".json", request.getMerge()));
        return new HttpEntity(body, headers);
    }

    private Resource createResource(final String name, final String suffix, final byte[] content) throws IOException {
        final Path tempFile = Files.createTempFile(name, suffix);
        Files.write(tempFile, content);
        final File file = tempFile.toFile();
        return new FileSystemResource(file);
    }
}
