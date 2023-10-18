package de.muenchen.oss.digiwf.address.integration.utility;

import de.muenchen.oss.digiwf.address.integration.TestMessageConsumer;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

public abstract class DigiWFIntegrationE2eTest {

    @Autowired
    private MessageApi messageApi;
    @Autowired
    private TestMessageConsumer testMessageConsumer;
    private final String messageTopic = "dwf-address-e2e-test";


    protected Map<String, Object> receiveMessage(final String processInstanceId) {
        return this.testMessageConsumer.receiveMessage(processInstanceId);
    }

    protected void sendMessage(final Object payload, final String processInstanceId, final String messageType) {
        final Map<String, Object> headers = Map.of(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId, DIGIWF_MESSAGE_NAME, "messageName", TYPE, messageType);
        messageApi.sendMessage(payload, headers, messageTopic);
    }

    protected Map<String, Object> runIntegration(final Object payload, final String processInstanceId, final String messageType) throws InterruptedException {
        this.sendMessage(payload, processInstanceId, messageType);
        // wait for a short moment for the integration to process the message
        Thread.sleep(200);
        final Map<String, Object> payloadVariables = this.receiveMessage(processInstanceId);
        if (payloadVariables != null) {
            return payloadVariables;
        }

        // try again
        Thread.sleep(1000);
        return this.receiveMessage(processInstanceId);
    }

}
