/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.api.resource;

import de.muenchen.oss.digiwf.legacy.dms.muc.api.mapper.MetadataTOMapper;
import de.muenchen.oss.digiwf.legacy.dms.muc.api.transport.GetMetadataTO;
import de.muenchen.oss.digiwf.legacy.dms.muc.api.transport.MetadataTO;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Rest Api to interact with the mucs-dms.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/dms")
@RequiredArgsConstructor
@Tag(name = "DmsRestController", description = "API to interact with the mucs-dms")
public class DmsRestController {

    private final DmsService dmsService;
    private final MetadataTOMapper metaDataMapper;
    private final AppAuthenticationProvider authenticationProvider;

    /**
     * Get the metadata for the given dms request.
     *
     * @param request Request data to query metadata
     * @return metadata
     */
    @PostMapping("/metadata")
    public ResponseEntity<MetadataTO> getMetaData(@RequestBody @Valid final GetMetadataTO request) {
        val userLogin = this.authenticationProvider.getCurrentUserId();
        val dmetaData = this.dmsService.getMetadata(userLogin, request.getUrl());
        return ResponseEntity.ok(this.metaDataMapper.map2TO(dmetaData));
    }

}
