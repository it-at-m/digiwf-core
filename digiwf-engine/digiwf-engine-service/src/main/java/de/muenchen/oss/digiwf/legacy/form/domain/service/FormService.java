/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.domain.service;

import de.muenchen.oss.digiwf.legacy.form.domain.mapper.FormMapper;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.form.domain.validator.ValidationHandler;
import de.muenchen.oss.digiwf.legacy.form.infrastructure.entity.FormEntity;
import de.muenchen.oss.digiwf.legacy.form.infrastructure.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service to interact with {@link Form}
 *
 * @author externer.dl.horn
 */
@Slf4j
@Deprecated
@Service("digitalWfFormService")
@RequiredArgsConstructor
public class FormService {

    private final FormMapper formMapper;
    private final FormRepository formRepository;
    private final List<ValidationHandler> validationHandlers;
    private final InAuthorizedGroupsPredicate inAuthorizedGroupsPredicate;

    /**
     * Get all forms.
     * Authorization check is performed.
     *
     * @return forms
     */
    public List<Form> getForms() {
        val formEntities = this.formRepository.findAll();
        final List<FormEntity> filteredEntities = formEntities.stream().filter(this.inAuthorizedGroupsPredicate).collect(Collectors.toList());
        return this.formMapper.map(filteredEntities);
    }

    /**
     * Get a form by key.
     *
     * @param formkey key of the form
     * @return form
     */
    public Optional<Form> getForm(final String formkey) {
        return this.formRepository.findByKey(formkey)
                .map(this.formMapper::map);
    }

    /**
     * Save a From.
     *
     * @param form form that is saved
     * @return form
     */
    public Form saveForm(final Form form) {
        val entity = this.formRepository.findByKey(form.getKey());
        val newEntity = this.formMapper.map2Entity(form);
        if (entity.isPresent()) {
            final FormEntity formEntity = entity.get();
            if (!this.inAuthorizedGroupsPredicate.test(formEntity))
                throw new AccessDeniedException(form.getKey());
            newEntity.setId(formEntity.getId());
        }
        val savedEntity = this.formRepository.save(newEntity);
        log.info("Form deployed: {}", savedEntity);

        return this.formMapper.map(savedEntity);
    }

    /**
     * Get a start form for the corres
     *
     * @param formKey Key of the startform
     * @return start form
     */
    public Optional<Form> getStartForm(final String formKey) {
        return this.getForm(formKey);
    }

    /**
     * @param formKey   Key of the form
     * @param variables Variables that should be validated
     * @return
     */
    public boolean validateVariables(final String formKey, final Map<String, Object> variables) {
        val form = this.getForm(formKey).get();
        return form.validateVariables(variables, this.validationHandlers);
    }
}
