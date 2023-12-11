package de.muenchen.oss.digiwf.cosys.integration.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.cosys.integration.api.GenerationApi;
import de.muenchen.oss.digiwf.cosys.integration.configuration.CosysConfiguration;
import de.muenchen.oss.digiwf.cosys.integration.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CosysAdapterTest {

    @Mock
    private CosysConfiguration configuration;

    @Mock
    private GenerationApi generationApi;

    @InjectMocks
    private CosysAdapter cosysAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCosysDocument_Success() throws IOException {
        // given
        val generateDocument = generateDocument();
        val response = "Response".getBytes();
        val responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);
        when(responseSpecMock.onStatus(any(), any())).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(byte[].class)).thenReturn(Mono.just(response));
        final ArgumentCaptor<File> dataFileCaptor = ArgumentCaptor.forClass(File.class);
        final ArgumentCaptor<File> mergeFileCaptor = ArgumentCaptor.forClass(File.class);

        when(generationApi.generatePdfWithResponseSpec(any(), any(), any(), dataFileCaptor.capture(), any(), any(), any(), any(), any(), any(), mergeFileCaptor.capture(), any(), any()))
                .thenReturn(responseSpecMock);

        when(configuration.getMergeOptions()).thenReturn("mergedata".getBytes());

        //when
        cosysAdapter.generateCosysDocument(generateDocument);

        //then

        verify(generationApi).generatePdfWithResponseSpec(
                generateDocument.getGuid(),
                generateDocument.getClient(),
                generateDocument.getRole(),
                dataFileCaptor.getValue(),
                null,
                null,
                null,
                null,
                null,
                false,
                mergeFileCaptor.getValue(),
                null,
                null
        );
    }

    private GenerateDocument generateDocument() {
        try {
            return new GenerateDocument(
                    "client",
                    "role",
                    "guid",
                    new ObjectMapper().readTree("{\"name\":\"John\", \"age\":30}"),
                    List.of(DocumentStorageUrl.builder().url("").build())
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
