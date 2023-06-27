package io.muenchendigital.digiwf.cosys.integration.example.api.controller;

import io.muenchendigital.digiwf.cosys.integration.application.port.out.GenerateDocumentPort;
import io.muenchendigital.digiwf.cosys.integration.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import io.muenchendigital.digiwf.message.common.MessageConstants;
import io.muenchendigital.digiwf.message.core.api.MessageApi;
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

    private final GenerateDocumentPort generateDocumentPort;
    private final MessageApi messageApi;

    @PostMapping(value = "/test/document")
    public ResponseEntity<byte[]> testCreateCosysDocument() {
        final byte[] file = this.generateDocumentPort.generateCosysDocument(this.generateDocument()).block();
        return ResponseEntity.ok(file);
    }

    /**
     * Note: for this to work, you have to configure both
     * spring.cloud.stream.bindings.sendMessage-out-0.destination and
     * spring.cloud.stream.bindings.functionRouter-in-0.destination
     * to the same topic.
     */
    @PostMapping(value = "/testEventBus")
    public void testEventBus(final @RequestBody DocumentStorageUrl body) {
        this.messageApi.sendMessage(body, Map.of(
                MessageConstants.TYPE, "createDocumentFromEventBus",
                MessageConstants.DIGIWF_PROCESS_INSTANCE_ID, "processInstanceId",
                MessageConstants.DIGIWF_MESSAGE_NAME, "testCosysIntegration"
        ), "dwf-cosys-local-01");
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
