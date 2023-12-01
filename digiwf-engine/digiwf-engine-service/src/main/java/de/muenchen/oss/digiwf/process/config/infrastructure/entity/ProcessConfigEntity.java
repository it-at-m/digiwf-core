/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.infrastructure.entity;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * Entity object of the {@link ProcessConfig}
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ServiceConfig")
@Table(name = "DWF_PROCESSCONFIG", indexes = {@Index(name = "IDX_DWF_PROCESSKEY", columnList = "KEY")})
public class ProcessConfigEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "KEY", nullable = false, unique = true)
    private String key;

    @Column(name = "VERSION", nullable = false)
    private String version;

    @Column(name = "CONFIG", columnDefinition = "CLOB")
    private String config;
}
