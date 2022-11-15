package io.muenchendigital.digiwf.cosys.integration.example.api.controller;

import io.muenchendigital.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.cosys.integration.domain.service.CosysService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final CosysService cosysService;
    private final PayloadSenderService genericPayloadSender;

    @PostMapping(value = "/test/document")
    public ResponseEntity<byte[]> testCreateCosysDocument() {
        final byte[] file = this.cosysService.generateCosysDocument(this.generateDocument()).block();
        return ResponseEntity.ok(file);
    }

    @PostMapping(value = "/testEventBus")
    public void testEventBus(final @RequestBody DocumentStorageUrl body) {
        this.genericPayloadSender.sendPayload(this.generateDocument(), "generateDocument");
    }

    private GenerateDocument generateDocument() {
        return GenerateDocument.builder()
                .client("9001")
                .role("TESTER")
                .guid("519650b7-87c2-41a6-8527-7b095675b13f")
                .variables(Map.of(
                        "FormField_Grusstext", "Hallo das ist mein Gruß",
                        "EmpfaengerVorname", "Dominik",
                        "AbsenderVorname", "Max"))
                .build();
    }

}
