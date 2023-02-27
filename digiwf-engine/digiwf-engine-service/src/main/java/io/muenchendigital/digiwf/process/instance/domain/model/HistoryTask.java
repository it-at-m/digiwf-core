/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.instance.domain.model;

import lombok.*;

import java.util.Date;

/**
 * Task that is completed.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class HistoryTask {

    /**
     * Name of the task.
     */
    private final String id;

    /**
     * Name of the task.
     */
    private final String name;

    /**
     * Time when the task was completed.
     */
    private final Date endTime;
}
