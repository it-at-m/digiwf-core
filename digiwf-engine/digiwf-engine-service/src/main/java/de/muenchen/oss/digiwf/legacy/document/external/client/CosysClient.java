/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.document.external.client;

import de.muenchen.oss.digiwf.legacy.document.external.model.GenerateDocumentTO;
import de.muenchen.oss.digiwf.legacy.document.external.properties.CosysProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosysClient {

    private final CosysProperties properties;

    private final RestTemplate restTemplate = new RestTemplate();

    public byte[] generateDocument(final String guid, final Map<String, Object> variables) {
        final String restCallFullUrl = this.properties.getAddress() + "camel/generation/pdf/";

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        val model = GenerateDocumentTO.builder()
                .guid(guid)
                .variables(this.parseVariables(variables))
                .build();

        final HttpEntity<GenerateDocumentTO> entity = new HttpEntity<>(model, headers);
        log.info("calling generateDocument: {}", guid);
        return this.restTemplate.postForObject(restCallFullUrl, entity, byte[].class);
    }

    private Map<String, String> parseVariables(final Map<String, Object> variables) {
        return variables.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue().toString()), HashMap::putAll);
    }

}
