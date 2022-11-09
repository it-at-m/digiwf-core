/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.domain.service;

import io.muenchendigital.digiwf.legacy.file.domain.mapper.FileMapper;
import io.muenchendigital.digiwf.legacy.file.domain.model.File;
import io.muenchendigital.digiwf.legacy.file.domain.model.NewFile;
import io.muenchendigital.digiwf.legacy.file.infrastructure.repository.FileRepository;
import io.muenchendigital.digiwf.shared.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * Handle Files
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    /**
     * Creates a file.
     *
     * @param newFile File that should be created
     * @return Created File
     */
    public File createFile(final NewFile newFile) {
        val file = new File(newFile);
        return this.saveFile(file);
    }

    /**
     * Get a File by Id.
     *
     * @param id Id of the file
     * @return The file
     */
    public File getFile(final String id) {
        val entity = this.fileRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("The File with the id %s is not available", id)));
        return this.fileMapper.map(entity);
    }

    /**
     * Delete all files by the given business key.
     *
     * @param businesskey The business key
     */
    public void deleteFileByBusinessKey(final String businesskey) {
        this.fileRepository.deleteAllByBusinessKey(businesskey);
    }

    // HELPER METHODS

    private File saveFile(final File file) {
        val savedFile = this.fileRepository.save(this.fileMapper.map2Entity(file));
        return this.fileMapper.map(savedFile);
    }

}
