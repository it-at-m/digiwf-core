package de.muenchen.oss.digiwf.address.integration.utility;

import de.muenchen.oss.digiwf.address.integration.TestMessageConsumer;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

public abstract class DigiWFIntegrationE2eTest {

    @Autowired
    private MessageApi messageApi;
    @Autowired
    private TestMessageConsumer testMessageConsumer;
    @Value("${spring.cloud.stream.bindings.functionRouter-in-0.destination}")
    private String messageTopic;


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
        Thread.sleep(800);
        final Map<String, Object> payloadVariables = this.receiveMessage(processInstanceId);
        if (payloadVariables != null) {
            return payloadVariables;
        }

        // try again in case it takes longer than expected
        Thread.sleep(3000);
        return this.receiveMessage(processInstanceId);
    }

}
