package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SearchSubjectAreaMessageProcessorTest extends MessageProcessorTestBase {

    private final SearchObjectDto searchSubjectAreaDto = new SearchObjectDto(
            "aktenplan",
            "user"
    );
    private Message<SearchObjectDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.when(searchSubjectAreaUseCase.searchSubjectArea(
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
        messageProcessor.searchSubjectArea().accept(this.message);
        verify(searchSubjectAreaUseCase, times(1)).searchSubjectArea(
                searchSubjectAreaDto.getSearchString(),
                searchSubjectAreaDto.getUser());
    }

}

