package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final MailingService mailingService;
    private final PayloadSenderService genericPayloadSender;

    @PostMapping(value = "/testSendMail")
    public void testSendMail(@RequestBody final Mail mail) {
        try {
            this.mailingService.sendMail(mail);
        } catch (final ConstraintViolationException e) {
            log.error(e.toString());
        }
    }

    /**
     * Note: for this to work, you have to configure both
     * spring.cloud.stream.bindings.sendMessage-out-0.destination and
     * spring.cloud.stream.bindings.functionRouter-in-0.destination
     * to the same topic.
     */
    @PostMapping(value = "/testEventBus")
    public void testEventBus(@RequestBody final Mail mail) {
        this.genericPayloadSender.sendPayload(mail, "sendMailFromEventBus");
    }

}
