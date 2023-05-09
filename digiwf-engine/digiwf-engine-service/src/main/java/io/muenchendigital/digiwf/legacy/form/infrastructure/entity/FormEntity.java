/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity representation of a form.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Form")
@Table(name = "DWF_FORM", indexes = {@Index(name = "IDX_DWF_FORMKEY", columnList = "KEY")})
public class FormEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "KEY", nullable = false, unique = true)
    private String key;

    @Column(name = "VERSION", nullable = false)
    private String version;

    @Column(name = "AUTHORIZATION_")
    private String authorization;

    @Column(name = "CONFIG", columnDefinition = "CLOB")
    private String config;
}
