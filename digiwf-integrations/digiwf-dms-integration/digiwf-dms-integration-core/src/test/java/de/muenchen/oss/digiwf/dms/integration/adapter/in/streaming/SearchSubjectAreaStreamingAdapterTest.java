package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SearchSubjectAreaStreamingAdapterTest extends StreamingAdapterTestBase {

    private final SearchObjectDto searchSubjectAreaDto = new SearchObjectDto(
            "aktenplan",
            "user",
            "test-reference",
            "test-value"
    );
    private Message<SearchObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(searchSubjectAreaInPort.searchSubjectArea(
                searchSubjectAreaDto.getSearchString(),
                searchSubjectAreaDto.getUser()
        )).thenReturn("coo");

        this.message = new Message<>() {
            @Override
            public SearchObjectDto getPayload() {
                return searchSubjectAreaDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testReadFileSuccessfully() {
        streamingAdapter.searchSubjectArea().accept(this.message);
        verify(searchSubjectAreaInPort, times(1)).searchSubjectArea(
                searchSubjectAreaDto.getSearchString(),
                searchSubjectAreaDto.getUser());
    }

}

