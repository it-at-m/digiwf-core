/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.alw.integration.api.controller;

import io.muenchendigital.digiwf.alw.integration.domain.exception.AlwException;
import io.muenchendigital.digiwf.alw.integration.domain.model.AlwPersoneninfoRequest;
import io.muenchendigital.digiwf.alw.integration.domain.service.AlwPersoneninfoService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final AlwPersoneninfoService alwPersoneninfoService;
    private final PayloadSenderService genericPayloadSender;

    @GetMapping(value = "/testGetAlwZustaendigkeit")
    public void testGetAlwZustaendigkeit() {
        try {
            alwPersoneninfoService.getZustaendigkeit(createAlwZustaendigkeitRequest());
        } catch (final AlwException e) {
            log.error("testGetAlwZustaendigkeit failed: {}", e.toString());
        }
    }

    @GetMapping(value = "/testGetAlwZustaendigkeitEventBus")
    public void testGetAlwZustaendigkeitEventBus() {
        genericPayloadSender.sendPayload(createAlwZustaendigkeitRequest(), "getAlwZustaendigkeitEventBus"); // send all messages to spring.cloud.stream.bindings.sendMessage-out-0.destination
    }

    private AlwPersoneninfoRequest createAlwZustaendigkeitRequest() {
        final AlwPersoneninfoRequest alwPersoneninfoRequest = new AlwPersoneninfoRequest();
        alwPersoneninfoRequest.setAzrNummer("123456789012");
        return alwPersoneninfoRequest;
    }

}
