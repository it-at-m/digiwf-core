package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

class MessageProcessorTestBase {
    protected final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    protected final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    protected final CreateFileInPort createFileInPortMock = Mockito.mock(CreateFileInPort.class);
    protected final CreateProcedureInPort createProcedureMock = Mockito.mock(CreateProcedureInPort.class);
    protected final CreateDocumentInPort createDocumentInPortMock = Mockito.mock(CreateDocumentInPort.class);
    protected final UpdateDocumentInPort updateDocumentInPortMock = Mockito.mock(UpdateDocumentInPort.class);
    protected final DepositObjectInPort depositObjectInPortMock = Mockito.mock(DepositObjectInPort.class);
    protected final CancelObjectInPort cancelObjectInPortMock = Mockito.mock(CancelObjectInPort.class);
    protected final ReadContentInPort readContentInPort = Mockito.mock(ReadContentInPort.class);
    protected final SearchFileInPort searchFileInPort = Mockito.mock(SearchFileInPort.class);
    protected final SearchSubjectAreaInPort searchSubjectAreaInPort = Mockito.mock(SearchSubjectAreaInPort.class);
    protected final String processInstanceId = "exampleProcessInstanceId";
    protected final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_INTEGRATION_NAME, "dmsIntegration", TYPE, "type"));
    protected MessageProcessor messageProcessor;

    protected void setupBase() {
        this.messageProcessor = new MessageProcessor(
                processApi,
                errorApiMock,
                createFileInPortMock,
                createProcedureMock,
                createDocumentInPortMock,
                updateDocumentInPortMock,
                depositObjectInPortMock,
                cancelObjectInPortMock,
                readContentInPort,
                searchFileInPort,
                searchSubjectAreaInPort);
    }
}

