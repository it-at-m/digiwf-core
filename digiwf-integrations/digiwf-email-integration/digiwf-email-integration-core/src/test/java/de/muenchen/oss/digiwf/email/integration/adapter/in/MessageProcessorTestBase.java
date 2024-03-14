package de.muenchen.oss.digiwf.email.integration.adapter.in;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMail;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

public class MessageProcessorTestBase {

    protected final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    protected final SendMail sendMailMock = Mockito.mock(SendMail.class);
    protected final MonitoringService monitoringServiceMock = Mockito.mock(MonitoringService.class);

    // dummy data
    protected final String processInstanceId = "exampleProcessInstanceId";
    protected final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_INTEGRATION_NAME, "emailIntegration", TYPE, "emailType"));

    protected MessageProcessor messageProcessor;

    protected void setupBase() {
        this.messageProcessor = new MessageProcessor(
                errorApiMock,
                sendMailMock,
                monitoringServiceMock);
    }

}
