package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateProcedureUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.DepositObjectUseCase;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;

class MessageProcessorTestBase {
    protected final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    protected final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    protected final CreateProcedureUseCase createProcedureMock = Mockito.mock(CreateProcedureUseCase.class);
    protected final CreateDocumentUseCase createDocumentUseCaseMock = Mockito.mock(CreateDocumentUseCase.class);
    protected final DepositObjectUseCase depositObjectUseCaseMock = Mockito.mock(DepositObjectUseCase.class);
    protected final String processInstanceId = "exampleProcessInstanceId";
    protected final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_MESSAGE_NAME, "messageName"));
    protected MessageProcessor messageProcessor;

    protected void setupBase() {
        this.messageProcessor = new MessageProcessor(processApi, errorApiMock, createProcedureMock, createDocumentUseCaseMock, depositObjectUseCaseMock);
    }
}

