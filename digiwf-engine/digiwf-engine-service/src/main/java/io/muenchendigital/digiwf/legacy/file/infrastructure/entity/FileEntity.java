/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * File Entity that is persisted in the database.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "File")
@Table(name = "DWF_FILE")
public class FileEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "DATA", columnDefinition = "CLOB")
    private String data;

    @Column(name = "TYPE")
    private String fileType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BUSINESSKEY")
    private String businessKey;
}
