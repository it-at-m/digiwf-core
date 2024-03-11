package de.muenchen.oss.digiwf.ticket.integration.adapter.in;

import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming.TicketMessageProcessor;
import de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming.WriteArticleDto;
import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;
import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.mockito.ArgumentMatchers.any;

class WriteArticleMessageProcessorTest {

    private WriteArticleInPort writeArticleInPort = Mockito.mock(WriteArticleInPort.class);

    private ProcessApi processApi = Mockito.mock(ProcessApi.class);

    private ErrorApi errorApi = Mockito.mock(ErrorApi.class);

    private TicketMessageProcessor messageProcessor = new TicketMessageProcessor(
            writeArticleInPort,
            processApi,
            errorApi);

    @BeforeEach
    void setup() {
        Mockito.doNothing().when(writeArticleInPort).writeArticle(any(), any(), any(), any(), any());
    }

    @NotNull
    private Message<WriteArticleDto> createmessage(WriteArticleDto writeArticleDto) {
        return new Message<>() {
            @Override
            public WriteArticleDto getPayload() {
                return writeArticleDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId", DIGIWF_INTEGRATION_NAME, "dmsIntegration", TYPE, "type", DIGIWF_PROCESS_DEFINITION, "processDefinition"));
            }
        };
    }

    @Test
    void testDmsIntegrationReadContentSuccessfully() {
        //given
        final WriteArticleDto writeArticleDto = new WriteArticleDto(
                "ticketID123",
                "mein text",
                "userID123",
                "OPEN",
                "test"
        );
        final Message message1 = createmessage(writeArticleDto);

        //when
        messageProcessor.writeArticle().accept(message1);

        //then
        Mockito.verify(writeArticleInPort, Mockito.times(1)).writeArticle(
                "ticketID123",
                new Article("mein text", "userID123"),
                TicketStatus.OPEN, List.of("test"), "processDefinition");
    }

}

