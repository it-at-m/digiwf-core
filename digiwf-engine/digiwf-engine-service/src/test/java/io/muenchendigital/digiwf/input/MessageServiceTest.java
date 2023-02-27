package io.muenchendigital.digiwf.input;

import io.muenchendigital.digiwf.engine.mapper.EngineDataMapperImpl;
import io.muenchendigital.digiwf.message.domain.model.CorrelateMessage;
import io.muenchendigital.digiwf.message.domain.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@RunWith(SpringRunner.class)
@Import({MessageService.class, EngineDataMapperImpl.class})
public class MessageServiceTest {

    @MockBean
    private RuntimeService runtimeService;

    @Autowired
    private MessageService messageService;

    @Mock
    private MessageCorrelationBuilder messageCorrelationBuilder;


    @Before
    public void before() {
        Mockito.when(this.messageCorrelationBuilder.processInstanceId(any())).thenReturn(this.messageCorrelationBuilder);
        Mockito.when(this.messageCorrelationBuilder.setVariables(any())).thenReturn(this.messageCorrelationBuilder);
        Mockito.when(this.runtimeService.createMessageCorrelation(any())).thenReturn(this.messageCorrelationBuilder);
        Mockito.doNothing().when(this.runtimeService).correlateMessage(any());
    }

    @Test
    public void messageCorrelationWithSimpleData() {
        final CorrelateMessage correlateMessage = CorrelateMessage.builder()
                .businessKey("test")
                .processInstanceId("myId")
                .payloadVariables(Map.of("test", "1"))
                .build();
        this.messageService.correlateMessage(correlateMessage);
        Mockito.verify(this.messageCorrelationBuilder).setVariables(Map.of("test", "1"));
    }


    @Test
    public void messageCorrelationWithComplexeData() {
        final CorrelateMessage correlateMessage = CorrelateMessage.builder()
                .businessKey("test")
                .processInstanceId("myId")
                .payloadVariables(Map.of(
                        "object", Map.of("key", "2"),
                        "array", List.of("1", "2", "3")
                ))
                .build();

        this.messageService.correlateMessage(correlateMessage);
        Mockito.verify(this.messageCorrelationBuilder).setVariables(any());
    }


}
