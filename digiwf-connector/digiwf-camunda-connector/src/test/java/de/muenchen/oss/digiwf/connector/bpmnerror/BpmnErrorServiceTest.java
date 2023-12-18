package de.muenchen.oss.digiwf.connector.bpmnerror;


import de.muenchen.oss.digiwf.camunda.connector.bpmnerror.BpmnErrorServiceImpl;
import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnError;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorService;
import de.muenchen.oss.digiwf.connector.bpmnerror.internal.impl.model.BpmnErrorImpl;
import org.camunda.community.rest.client.api.MessageApi;
import org.junit.jupiter.api.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("Bpmn error Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BpmnErrorServiceTest {
    private final MessageApi messageApi = mock(MessageApi.class);

    private final EngineDataSerializer engineDataSerializer = new EngineDataSerializer();

    private BpmnErrorService bpmnErrorService;

    @BeforeEach
    void initTests() {
        this.bpmnErrorService = new BpmnErrorServiceImpl(this.messageApi, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate bpmn error")
    void shouldCorrelateBpmnError() {

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
