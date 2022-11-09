/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.humantask.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Object to complete a task.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTO {

    /**
     * Id of the task the should be completed.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are set during completion.
     * Only variables that are contained in the associated form are valid.
     */
    @NotNull
    private Map<String, Object> variables;

}
