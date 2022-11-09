/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.infrastructure.repository;

import io.muenchendigital.digiwf.legacy.file.infrastructure.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File repository to perform operations on the database.
 *
 * @author externer.dl.horn
 */
public interface FileRepository extends JpaRepository<FileEntity, String> {

    /**
     * Delete all files by business key
     *
     * @param businessKey Business key of the corresponding process
     */
    void deleteAllByBusinessKey(String businessKey);
}
