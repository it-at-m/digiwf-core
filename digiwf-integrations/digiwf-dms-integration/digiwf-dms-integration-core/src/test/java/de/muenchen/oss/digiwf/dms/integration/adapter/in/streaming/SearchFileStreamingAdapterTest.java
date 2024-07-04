package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SearchFileStreamingAdapterTest extends StreamingAdapterTestBase {

    private final SearchObjectDto searchFileDto = new SearchObjectDto(
            "group.*-file-*",
            "user",
            "test-reference",
            "test-value"
    );
    private Message<SearchObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(searchFileInPort.searchFile(
                searchFileDto.getSearchString(),
                searchFileDto.getUser(),
                searchFileDto.getReference(),
                searchFileDto.getValue()
        )).thenReturn(List.of("noFilter"));

        this.message = new Message<>() {
            @Override
            public SearchObjectDto getPayload() {
                return searchFileDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testReadFileSuccessfully() {
        streamingAdapter.searchFile().accept(this.message);
        verify(searchFileInPort, times(1)).searchFile(
                searchFileDto.getSearchString(),
                searchFileDto.getUser(),
                searchFileDto.getReference(),
                searchFileDto.getValue());
    }

}

