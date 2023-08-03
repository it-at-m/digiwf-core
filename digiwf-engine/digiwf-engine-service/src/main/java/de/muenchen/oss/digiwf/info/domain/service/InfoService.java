/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.info.domain.service;

import de.muenchen.oss.digiwf.info.domain.mapper.InfoMapper;
import de.muenchen.oss.digiwf.info.domain.model.Info;
import de.muenchen.oss.digiwf.info.infrastructure.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * Service to interact with {@link Info}
 *
 * @author martin.dietrich
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InfoService {

    private final InfoMapper infoMapper;
    private final InfoRepository infoRepository;

    /**
     * Get info.
     *
     * @return info
     */
    public Info getInfo() {
        val infoEntity = this.infoRepository.findFirstBy();
        return this.infoMapper.map(infoEntity);
    }

}
