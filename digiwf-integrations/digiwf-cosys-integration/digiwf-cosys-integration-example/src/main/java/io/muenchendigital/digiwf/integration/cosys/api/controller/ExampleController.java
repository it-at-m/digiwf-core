package io.muenchendigital.digiwf.integration.cosys.api.controller;

import io.muenchendigital.digiwf.integration.cosys.domain.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.integration.cosys.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.integration.cosys.domain.service.CosysService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final CosysService cosysService;
    private final PayloadSenderService genericPayloadSender;

    @PostMapping(value = "/test/document")
    public void testCreateCosysDocument(final @RequestBody DocumentStorageUrl body) {
        try {
            this.cosysService.createDocument(this.generateDocument(body));
            log.info("Generated document successfully. Checkout {} in your s3 storage.", body.getPath());
        } catch (final Exception e) {
            log.error(e.toString());
        }
    }

    @PostMapping(value = "/testEventBus")
    public void testEventBus(final @RequestBody DocumentStorageUrl body) {
        this.genericPayloadSender.sendPayload(this.generateDocument(body), "generateDocument");
    }

    private GenerateDocument generateDocument(final DocumentStorageUrl documentStorageUrl) {
        return GenerateDocument.builder()
                .client("9001")
                .role("TESTER")
                .guid("519650b7-87c2-41a6-8527-7b095675b13f")
                .variables(Map.of(
                        "FormField_Grusstext", "Hallo das ist mein Gruß",
                        "EmpfaengerVorname ", "Dominik",
                        "AbsenderVorname ", "Max"))
                .documentStorageUrls(List.of(documentStorageUrl))
                .build();
    }

}
