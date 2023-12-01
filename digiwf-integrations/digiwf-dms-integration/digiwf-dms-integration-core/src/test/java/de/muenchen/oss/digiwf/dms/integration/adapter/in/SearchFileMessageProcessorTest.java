package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SearchFileMessageProcessorTest extends MessageProcessorTestBase {

    private final SearchObjectDto searchFileDto = new SearchObjectDto(
            "group.*-file-*",
            "user"
    );
    private Message<SearchObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(searchFileUseCase.searchFile(
                searchFileDto.getSearchString(),
                searchFileDto.getUser()
        )).thenReturn("coo");

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
        messageProcessor.searchFile().accept(this.message);
        verify(searchFileUseCase, times(1)).searchFile(
                searchFileDto.getSearchString(),
                searchFileDto.getUser());
    }

}

