package io.muenchendigital.digiwf.cosys.integration.domain.service;

import io.muenchendigital.digiwf.cosys.integration.domain.mapper.GenerateDocumentRequestMapper;
import io.muenchendigital.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocumentRequest;
import io.muenchendigital.digiwf.cosys.integration.gen.api.GenerationApi;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosysService {

    private final S3FileTransferRepository s3FileTransferRepository;
    private final GenerateDocumentRequestMapper generateDocumentRequestMapper;
    private final GenerationApi generationApi;


    private final WebClient webClient;

    static final String ATTRIBUTE_CLIENT = "client";
    static final String ATTRIBUTE_ROLE = "role";
    static final String ATTRIBUTE_STATE_FILTER = "stateFilter";
    static final String ATTRIBUTE_VALIDITY = "validity";

    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    public void createDocument(@Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateCosysDocument(generateDocument).block();
        this.saveDocumentInS3(generateDocument, data);
    }

    /**
     * Generate a Document in Cosys
     *
     * @param generateDocument Data for generating documents
     * @return
     */
    public Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument) {
        try {
            final GenerateDocumentRequest generateDocumentRequest = this.generateDocumentRequestMapper.map(generateDocument);
            return this.generationApi.generatePdf(
                    generateDocumentRequest.getGuid(),
                    generateDocumentRequest.getClient(),
                    generateDocumentRequest.getRole(),
                    this.createFile("data", ".json", generateDocumentRequest.getData()),
                    null,
                    generateDocumentRequest.getStateFilter(),
                    generateDocumentRequest.getValidity(),
                    null,
                    null,
                    false,
                    this.createFile("merge", ".json", generateDocumentRequest.getMerge()),
                    null,
                    null
            );
        } catch (final Exception ex) {
            log.error("Document could not be created.", ex);
            throw new RuntimeException("Document could not be created.");
        }
    }

    private File createFile(final String name, final String suffix, final byte[] content) throws IOException {
        final Path tempFile = Files.createTempFile(name, suffix);
        Files.write(tempFile, content);
        return tempFile.toFile();
    }


    byte[] postForObject(final GenerateDocumentRequest generateDocument) {

        final MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("data", generateDocument.getData())
                .filename("data.json")
                .contentType(MediaType.APPLICATION_JSON);

        builder.part("merge", generateDocument.getMerge())
                .filename("merge.json")
                .contentType(MediaType.APPLICATION_JSON);


        final Mono<byte[]> body = this.webClient.post()
                .uri(uriBuilder -> this.createCosysURI(generateDocument, uriBuilder))
                .attributes(
                        ServerOAuth2AuthorizedClientExchangeFilterFunction
                                .clientRegistrationId("cosys"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build())
                )
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class) // error body as String or other class
                        .flatMap(error -> Mono.error(new RuntimeException(error)))) // throw a functional exception
                .bodyToMono(byte[].class);

        return body.block();
    }

    //------------------------------------------ helper methods ------------------------------------------//

    private void saveDocumentInS3(final GenerateDocument generateDocument, final byte[] data) {
        try {
            for (final DocumentStorageUrl presignedUrl : generateDocument.getDocumentStorageUrls()) {
                if (presignedUrl.getAction().equalsIgnoreCase("POST")) {
                    this.s3FileTransferRepository.saveFile(presignedUrl.getUrl(), data);
                } else if (presignedUrl.getAction().equalsIgnoreCase("PUT")) {
                    this.s3FileTransferRepository.updateFile(presignedUrl.getUrl(), data);
                } else {
                    throw new RuntimeException("Document could not be saved.");
                }
            }
        } catch (final DocumentStorageClientErrorException | DocumentStorageServerErrorException | DocumentStorageException ex) {
            log.error("Document could not be saved.", ex);
            throw new RuntimeException("Document could not be saved.");
        }
    }

    private URI createCosysURI(final GenerateDocumentRequest request, final UriBuilder uriBuilder) {
        uriBuilder.path("/generation/" + request.getGuid() + "/pdf");
        uriBuilder.queryParam(ATTRIBUTE_CLIENT, request.getClient())
                .queryParam(ATTRIBUTE_ROLE, request.getRole())
                .queryParam("throwExceptionOnFailure", true)
                .queryParam("connectTimeout", 1500);

        if (!StringUtils.isEmpty(request.getStateFilter())) {
            uriBuilder.queryParam(ATTRIBUTE_STATE_FILTER, request.getStateFilter());
        }

        if (!StringUtils.isEmpty(request.getStateFilter())) {
            uriBuilder.queryParam(ATTRIBUTE_VALIDITY, request.getValidity());
        }
        return uriBuilder.build();
    }

}
