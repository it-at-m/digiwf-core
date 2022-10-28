package io.muenchendigital.digiwf.integration.cosys.api.controller;

import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.integration.cosys.domain.service.CosysService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final CosysService cosysService;
    private final PayloadSenderService genericPayloadSender;

    @GetMapping(value = "/test/document")
    public void testSendMail() {
        try {
            this.cosysService.createDocument(this.generateDocument());
        } catch (final Exception e) {
            log.error(e.toString());
        }
    }

    @GetMapping(value = "/testEventBus")
    public void testEventBus() {
        this.genericPayloadSender.sendPayload(this.generateDocument(), "generateDocument");
    }

    private GenerateDocument generateDocument() {
        return GenerateDocument.builder()
                .client("9001")
                .role("TESTER")
                .s3Path("test/path/test.pdf")
                .guid("519650b7-87c2-41a6-8527-7b095675b13f")
                .variables(Map.of(
                        "FormField_Grusstext", "Hallo das ist mein Gruß",
                        "EmpfaengerVorname ", "Dominik",
                        "AbsenderVorname ", "Max"))
                .build();
    }

}
