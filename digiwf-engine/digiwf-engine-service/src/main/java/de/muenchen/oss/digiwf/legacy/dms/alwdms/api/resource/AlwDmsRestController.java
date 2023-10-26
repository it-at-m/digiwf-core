/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.api.resource;

import de.muenchen.oss.digiwf.legacy.dms.alwdms.api.mapper.AlwMetadataTOMapper;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.api.transport.AlwMetaDataTO;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.api.transport.GetAlwMetadataTO;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.service.AlwDmsService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Api to interact with the alw dms.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/alwdms")
@RequiredArgsConstructor
@Tag(name = "AlwDmsRestController", description = "API to interact with the alw dms")
public class AlwDmsRestController {

    private final AlwDmsService kvrDmsService;
    private final AlwMetadataTOMapper kvrMetaDataMapper;
    private final AppAuthenticationProvider authenticationProvider;

    /**
     * Get the metadata for the given dms request.
     *
     * @param request Request data to query metadata
     * @return metadata
     */
    @PostMapping("/metadata")
    public ResponseEntity<AlwMetaDataTO> getMetadata(@RequestBody @Valid final GetAlwMetadataTO request) {
        final AlwMetadata metadata = this.kvrDmsService.getContentMetadata(this.authenticationProvider.getCurrentUserId(), request.getUrl());
        return ResponseEntity.ok(this.kvrMetaDataMapper.map2TO(metadata));
    }

}
