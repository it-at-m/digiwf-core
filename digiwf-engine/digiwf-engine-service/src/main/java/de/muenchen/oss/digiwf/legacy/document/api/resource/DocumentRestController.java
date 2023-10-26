/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.document.api.resource;

import de.muenchen.oss.digiwf.legacy.document.api.transport.StatusDokumentTO;
import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API to interact with documents.
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/document")
@RequiredArgsConstructor
@Tag(name = "DocumentRestController", description = "API to interact with documents")
public class DocumentRestController {

    private final DocumentService documentService;
    private final AppAuthenticationProvider authenticationProvider;

    @GetMapping("/task/{id}")
    public ResponseEntity<StatusDokumentTO> getStatusDokumentForTask(@PathVariable("id") final String taskId) {
        final String userLogin = this.authenticationProvider.getCurrentUserId();
        final byte[] data = this.documentService.getStatusDokumentForTask(taskId, userLogin);
        return ResponseEntity.ok(StatusDokumentTO.builder().data(data).build());
    }

}
