package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReadFilesMessageProcessorTest extends MessageProcessorTestBase {

    private final ReadFilesDto readFilesDto = new ReadFilesDto(
            List.of("fileCoo"),
            "filepath",
            "filecontext",
            "user"
    );
    private Message<ReadFilesDto> message;

    @BeforeEach
    void setup() {
        setupBase();
        Mockito.doNothing().when(readFilesUseCaseMock).readFiles(
                readFilesDto.getFileCoos(),
                readFilesDto.getUser(),
                readFilesDto.getFilePath(),
                readFilesDto.getFileContext());


        this.message = new Message<>() {
            @Override
            public ReadFilesDto getPayload() {
                return readFilesDto;
            }

            @Override
            public MessageHeaders getHeaders() {
                return messageHeaders;
            }
        };
    }

    @Test
    void testDmsIntegrationReadFilesSuccessfully() {
        messageProcessor.readFiles().accept(this.message);
        verify(readFilesUseCaseMock, times(1)).readFiles(
                readFilesDto.getFileCoos(),
                readFilesDto.getUser(),
                readFilesDto.getFilePath(),
                readFilesDto.getFileContext());
    }

}

