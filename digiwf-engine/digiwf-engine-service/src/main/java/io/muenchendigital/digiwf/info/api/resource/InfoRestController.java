/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.info.api.resource;

import io.muenchendigital.digiwf.info.api.mapper.InfoTOMapper;
import io.muenchendigital.digiwf.info.api.transport.InfoTO;
import io.muenchendigital.digiwf.info.domain.service.InfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API to handle app info.
 *
 * @author martin.dietrich
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/info")
@RequiredArgsConstructor
@Tag(name = "InfoRestController", description = "API to handle app infos")
public class InfoRestController {

    private final InfoService infoService;
    private final InfoTOMapper infoMapper;

    /**
     * Get info.
     *
     * @return info
     */
    @GetMapping
    public ResponseEntity<InfoTO> getInfo() {
        val info = this.infoService.getInfo();
        return ResponseEntity.ok(this.infoMapper.map2TO(info));
    }

}
