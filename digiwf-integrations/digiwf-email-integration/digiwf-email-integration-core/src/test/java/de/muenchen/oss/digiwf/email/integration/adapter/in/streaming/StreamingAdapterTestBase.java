package de.muenchen.oss.digiwf.email.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPathsInPort;
import de.muenchen.oss.digiwf.email.integration.application.port.in.SendMailPresignedInPort;
import de.muenchen.oss.digiwf.email.integration.infrastructure.MonitoringService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

public class StreamingAdapterTestBase {

    protected final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    protected final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    protected final SendMailPresignedInPort sendMailPresignedInPortMock = Mockito.mock(SendMailPresignedInPort.class);
    protected final SendMailPathsInPort sendMailPathsInPortMock = Mockito.mock(SendMailPathsInPort.class);
    protected final MonitoringService monitoringServiceMock = Mockito.mock(MonitoringService.class);

    // dummy data
    protected final String processInstanceId = "exampleProcessInstanceId";
    protected final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_INTEGRATION_NAME, "emailIntegration", TYPE, "emailType"));

    protected StreamingAdapter streamingAdapter;

    protected void setupBase() {
        this.streamingAdapter = new StreamingAdapter(
                processApi,
                errorApiMock,
                sendMailPresignedInPortMock,
                sendMailPathsInPortMock,
                monitoringServiceMock);
    }

}
