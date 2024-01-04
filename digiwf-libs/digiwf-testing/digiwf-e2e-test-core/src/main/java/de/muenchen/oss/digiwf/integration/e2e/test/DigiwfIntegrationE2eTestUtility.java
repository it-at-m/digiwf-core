package de.muenchen.oss.digiwf.integration.e2e.test;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.awaitility.Awaitility.await;

@Component
@RequiredArgsConstructor
public class DigiwfIntegrationE2eTestUtility {

    private final MessageApi messageApi;
    private final TestMessageConsumer testMessageConsumer;
    @Value("${spring.cloud.stream.bindings.functionRouter-in-0.destination}")
    private String messageTopic;

    private final int DEFAULT_TIMEOUT = 15;

    public Map<String, Object> runIntegration(final Object payload, final String processInstanceId, final String messageType) {
        return this.runIntegration(payload, processInstanceId, messageType, DEFAULT_TIMEOUT);
    }

    public Map<String, Object> runIntegration(final Object payload, final String processInstanceId, final String messageType, final int timeout) {
        this.sendMessage(payload, processInstanceId, messageType);

        // wait for the message to be received
        await().atMost(timeout, TimeUnit.SECONDS).until(() -> this.testMessageConsumer.hasReceivedMessage(processInstanceId));

        return this.testMessageConsumer.receiveMessage(processInstanceId);
    }

    private void sendMessage(final Object payload, final String processInstanceId, final String messageType) {
        final Map<String, Object> headers = Map.of(DIGIWF_PROCESS_INSTANCE_ID, processInstanceId, DIGIWF_MESSAGE_NAME, "messageName", TYPE, messageType);
        messageApi.sendMessage(payload, headers, messageTopic);
    }

}
