package io.muenchendigital.digiwf.connector.bpmnerror;


import io.muenchendigital.digiwf.camunda.connector.bpmnerror.BpmnErrorServiceImpl;
import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.connector.BaseSpringTest;
import io.muenchendigital.digiwf.connector.api.bpmnerror.BpmnError;
import io.muenchendigital.digiwf.connector.api.bpmnerror.BpmnErrorService;
import io.muenchendigital.digiwf.connector.bpmnerror.internal.impl.model.BpmnErrorImpl;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayName("Bpmn error Service Test")
@Import({EngineDataSerializer.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BpmnErrorServiceTest extends BaseSpringTest {

    @Mock
    private MessageApi messageApi;

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    private BpmnErrorService bpmnErrorService;

    @BeforeEach
    private void initTests() {
        this.bpmnErrorService = new BpmnErrorServiceImpl(this.messageApi, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate bpmn error")
    public void shouldCorrelateBpmnError() throws ApiException {

        final BpmnError bpmnError = BpmnErrorImpl.builder()
                .messageName("myMessage")
                .processInstanceId("myId")
                .errorCode("999")
                .errorMessage("myErrorMessage")
                .build();

        this.bpmnErrorService.createBpmnError(bpmnError);

        verify(this.messageApi).deliverMessage(any());
    }

}
