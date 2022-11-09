/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Object to save a task.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTO {

    /**
     * Id of the task.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are saved.
     * Only variables that are contained in the associated form are valid.
     */
    private Map<String, Object> variables;

}
