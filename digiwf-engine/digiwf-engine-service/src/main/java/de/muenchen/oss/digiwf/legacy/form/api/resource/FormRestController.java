/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.resource;

import de.muenchen.oss.digiwf.legacy.form.api.mapper.FormTOMapper;
import de.muenchen.oss.digiwf.legacy.form.api.transport.FormTO;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.form.domain.service.FormService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Rest API to handle forms.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/form")
@RequiredArgsConstructor
@Tag(name = "FormRestController", description = "API to handle forms")
public class FormRestController {

    private final FormService formService;
    private final FormTOMapper formMapper;

    /**
     * Get all forms.
     *
     * @return forms
     */
    @GetMapping
    public ResponseEntity<List<FormTO>> getForms() {
        val forms = this.formService.getForms();
        return ResponseEntity.ok(this.formMapper.map2TO(forms));
    }

    /**
     * Update an existing form.
     *
     * @param formKey Key of the form
     * @param to      Update that should be applied
     * @return form
     */
    @PutMapping("/{key}")
    @PreAuthorize("hasAuthority(T(de.muenchen.oss.digiwf.shared.security.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<FormTO> updateForm(@PathVariable("key") @NotBlank final String formKey, @RequestBody @Valid final FormTO to) {
        if (this.formService.getForm(formKey).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return this.createForm(to);
    }

    /**
     * Create a new form.
     *
     * @param to Form that should be created
     * @return form
     */
    @PostMapping
    @PreAuthorize("hasAuthority(T(de.muenchen.oss.digiwf.shared.security.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<FormTO> createForm(@RequestBody @Valid final FormTO to) {
        final Form form = this.formMapper.map(to);
        val savedForm = this.formService.saveForm(form);
        final FormTO savedTO = this.formMapper.map2TO(savedForm);
        return ResponseEntity.ok(savedTO);
    }

    /**
     * Get the form by key.
     *
     * @param formKey key of the form
     * @return form
     */
    @GetMapping("/{key}")
    public ResponseEntity<FormTO> getForm(@PathVariable("key") @NotBlank final String formKey) {
        val form = this.formService.getForm(formKey).orElseThrow();
        final FormTO to = this.formMapper.map2TO(form);
        return ResponseEntity.ok(to);
    }

}
