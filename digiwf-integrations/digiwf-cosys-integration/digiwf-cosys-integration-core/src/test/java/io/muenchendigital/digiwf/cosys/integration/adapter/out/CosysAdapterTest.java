package io.muenchendigital.digiwf.cosys.integration.adapter.out;

import io.muenchendigital.digiwf.cosys.integration.configuration.CosysConfiguration;
import io.muenchendigital.digiwf.cosys.integration.gen.api.GenerationApi;
import io.muenchendigital.digiwf.cosys.integration.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import org.junit.jupiter.api.Test;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;

class CosysAdapterTest {

    private CosysAdapter cosysAdapter;

    private final GenerationApi generationApi = mock(GenerationApi.class);

    private CosysConfiguration configuration = new CosysConfiguration();

    private final DocumentStorageUrl documentStorageUrl = new DocumentStorageUrl("URL", "Path", "POST");
    private List<DocumentStorageUrl> listOfURls = List.of(documentStorageUrl);
    private Map<String,String> variables = Map.of("key1", "value");
    private final GenerateDocument generateDocument = new GenerateDocument("Client", "Role", "guid", variables, listOfURls);

    @Test
    void generateCosysDocument() {
        configuration.setMergeOptions("MergeOptions".getBytes());
        configuration.setUrl("URL");
        cosysAdapter = new CosysAdapter(configuration, generationApi);
        when(generationApi.generatePdf(any(String.class),any(String.class),any(String.class),any(File.class),any(),any(),any(),any(),any(),any(),any(File.class),any(),any())).thenReturn(Mono.just("Document".getBytes()));

        Mono<byte[]> document = cosysAdapter.generateCosysDocument(generateDocument);
        assertNotNull(document);

        verify(generationApi).generatePdf(any(String.class),any(String.class),any(String.class),any(File.class),any(),any(),any(),any(),any(),any(),any(File.class),any(),any());
        verifyNoMoreInteractions(generationApi);
    }

    @Test
    void generateCosysDocumentThrowsIOException() {
        cosysAdapter = new CosysAdapter(configuration, generationApi);
        when(generationApi.generatePdf(anyString(),anyString(),anyString(),any(),anyString(),anyString(),anyString(),any(),anyString(),any(),any(),any(),any())).thenReturn(Mono.just("Document".getBytes()));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> {cosysAdapter.generateCosysDocument(generateDocument);});

        assertEquals("COSYS_DOCUMENT_CREATION_FAILED", bpmnError.getErrorCode());

        verifyNoInteractions(generationApi);
    }

    @Test
    void generateCosysDocumentThrowsWebClientResponseException() {
        configuration.setMergeOptions("MergeOptions".getBytes());
        configuration.setUrl("URL");
        cosysAdapter = new CosysAdapter(configuration, generationApi);
        when(generationApi.generatePdf(any(String.class),any(String.class),any(String.class),any(File.class),any(),any(),any(),any(),any(),any(),any(File.class),any(),any())).thenThrow(new WebClientResponseException(1,"Text",null,null,null));

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> {cosysAdapter.generateCosysDocument(generateDocument);});

        assertEquals("COSYS_DOCUMENT_CREATION_FAILED", bpmnError.getErrorCode());
        assertEquals("1 Text", bpmnError.getErrorMessage());

        verify(generationApi).generatePdf(any(String.class),any(String.class),any(String.class),any(File.class),any(),any(),any(),any(),any(),any(),any(File.class),any(),any());
        verifyNoMoreInteractions(generationApi);

    }
}