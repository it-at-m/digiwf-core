package de.muenchen.oss.digiwf.input;

import de.muenchen.oss.digiwf.engine.mapper.EngineDataMapper;
import de.muenchen.oss.digiwf.message.domain.model.CorrelateMessage;
import de.muenchen.oss.digiwf.message.domain.service.MessageService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

  private final RuntimeService runtimeService = mock(RuntimeService.class);
  private final EngineDataMapper engineDataMapper = mock(EngineDataMapper.class);

  private final MessageService messageService = new MessageService(
      runtimeService,
      engineDataMapper
  );

  private final MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);

  @BeforeEach
  public void before() {
    when(this.messageCorrelationBuilder.processInstanceId(any())).thenReturn(this.messageCorrelationBuilder);
    when(this.messageCorrelationBuilder.setVariables(any())).thenReturn(this.messageCorrelationBuilder);
    when(this.runtimeService.createMessageCorrelation(any())).thenReturn(this.messageCorrelationBuilder);
    doNothing().when(this.runtimeService).correlateMessage(any());
  }

  @Test
  public void messageCorrelationWithSimpleData() {
    final CorrelateMessage correlateMessage = CorrelateMessage.builder()
        .businessKey("test")
        .processInstanceId("myId")
        .payloadVariables(Map.of("test", "1"))
        .build();

    when(this.engineDataMapper.mapObjectsToVariables(Map.of("test", "1"))).thenReturn(Map.of("test", "1"));
    this.messageService.correlateMessage(correlateMessage);
    verify(this.messageCorrelationBuilder).setVariables(Map.of("test", "1"));
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
    verify(this.messageCorrelationBuilder).setVariables(any());
  }
}
