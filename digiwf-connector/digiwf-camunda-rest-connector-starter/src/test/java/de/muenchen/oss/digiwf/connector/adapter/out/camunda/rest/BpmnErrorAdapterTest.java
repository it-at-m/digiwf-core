package de.muenchen.oss.digiwf.connector.adapter.out.camunda.rest;


import de.muenchen.oss.digiwf.connector.adapter.BaseSpringTest;
import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import org.camunda.community.rest.client.api.MessageApi;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayName("Bpmn error Service Test")
@Import({ToEngineDataMapper.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BpmnErrorAdapterTest extends BaseSpringTest {

    @Mock
    private MessageApi messageApi;

    @Autowired
    private ToEngineDataMapper toEngineDataMapper;

    private BpmnErrorAdapter bpmnErrorService;

    @BeforeEach
    private void initTests() {
        this.bpmnErrorService = new BpmnErrorAdapter(this.messageApi, this.toEngineDataMapper);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate bpmn error")
    public void shouldCorrelateBpmnError() {

        final BpmnError bpmnError = BpmnError.builder()
                .messageName("myMessage")
                .processInstanceId("myId")
                .errorCode("999")
                .errorMessage("myErrorMessage")
                .build();

        this.bpmnErrorService.createBpmnError(bpmnError);

        verify(this.messageApi).deliverMessage(any());
    }

}
