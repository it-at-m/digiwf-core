package de.muenchen.oss.digiwf.email.integration.api.controller;

import de.muenchen.oss.digiwf.email.integration.model.Mail;
import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final MessageApi messageApi;

    /**
     * Note: for this to work, you have to configure both
     * spring.cloud.stream.bindings.sendMessage-out-0.destination and
     * spring.cloud.stream.bindings.functionRouter-in-0.destination
     * to the same topic.
     */
    @PostMapping(value = "/testEventBus")
    public void testEventBus(@RequestBody final Mail mail) {
        this.messageApi.sendMessage(mail, Map.of(
                MessageConstants.TYPE, "sendMailFromEventBus",
                MessageConstants.DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId",
                MessageConstants.DIGIWF_MESSAGE_NAME, "testEmailIntegration"
        ), "dwf-email-local-01");
    }

}
