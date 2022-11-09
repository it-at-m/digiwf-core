/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.definition.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Transport object to start a new process instance.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartInstanceTO {

    /**
     * Key of the process that is started.
     */
    private String key;

    /**
     * Variables that are passed to the new process instance.
     */
    private Map<String, Object> variables;

}
