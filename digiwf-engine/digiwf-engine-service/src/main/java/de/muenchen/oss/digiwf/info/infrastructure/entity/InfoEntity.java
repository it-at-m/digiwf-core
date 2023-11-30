/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.info.infrastructure.entity;

import lombok.*;

import jakarta.persistence.*;

/**
 * Entity representation of an info.
 *
 * @author martin.dietrich
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Info")
@Table(name = "DWF_INFO")
public class InfoEntity {

    @Id
    @Column(name = "TBLLOCK", columnDefinition = "char", unique = true, nullable = false, length = 1)
    private String tbllock;

    @Column(name = "MAINTENANCE_INFO1")
    private String maintenanceInfo1;

    @Column(name = "MAINTENANCE_INFO2")
    private String maintenanceInfo2;

    @Column(name = "ENVIRONMENT")
    private String environment;


}
