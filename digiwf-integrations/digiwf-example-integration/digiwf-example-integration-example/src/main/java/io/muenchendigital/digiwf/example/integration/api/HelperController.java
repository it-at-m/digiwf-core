package io.muenchendigital.digiwf.example.integration.api;

import io.muenchendigital.digiwf.example.integration.core.adapter.ExampleDto;
import io.muenchendigital.digiwf.message.common.MessageConstants;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HelperController {

    private final MessageApi messageApi;

    @GetMapping
    public void test() {
        final String targetTopic = "digiwf-example-integration-local-01";

        final Map<String, Object> headers = new HashMap<>(Map.of(
                MessageConstants.TYPE, "exampleIntegration",
                MessageConstants.DIGIWF_PROCESS_INSTANCE_ID, "123456789",
                MessageConstants.DIGIWF_MESSAGE_NAME, "test"
        ));

        this.messageApi.sendMessage(
                new ExampleDto("some data"),
                headers,
                targetTopic
        );

        // should raise a bpmn error
        headers.put(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID, "987654321");
        this.messageApi.sendMessage(
                new ExampleDto(null),
                headers,
                targetTopic
        );
    }
}
