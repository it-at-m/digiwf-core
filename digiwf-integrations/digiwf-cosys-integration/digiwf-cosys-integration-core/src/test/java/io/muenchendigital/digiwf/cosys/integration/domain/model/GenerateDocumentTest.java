package io.muenchendigital.digiwf.cosys.integration.domain.model;

import io.muenchendigital.digiwf.cosys.integration.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Verify bean validation of {@link GenerateDocument}
 *
 * @author ext.dl.moesle
 */
public class GenerateDocumentTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testGenerateDocumentValidationSuccess() {
        List.of("POST", "PUT").forEach(action -> {
            final DocumentStorageUrl validDocumentStorageUrl = DocumentStorageUrl.builder()
                    .url("http://localhost:8080")
                    .action(action)
                    .path("/path/to/file")
                    .build();
            final GenerateDocument validDocument = GenerateDocument.builder()
                    .client("client")
                    .role("role")
                    .guid("guid")
                    .variables(new HashMap<>())
                    .documentStorageUrls(List.of(validDocumentStorageUrl))
                    .build();
            final Set<ConstraintViolation<GenerateDocument>> v = this.validator.validate(validDocument);
            Assertions.assertTrue(v.isEmpty());
        });
    }

    @Test
    void testGenerateDocumentValidationFailsForMissingArgument() {
        final Set<ConstraintViolation<GenerateDocument>> violations;
        violations = this.validator.validate(
                GenerateDocument.builder()
                        .build()
        );
        Assertions.assertEquals(3, violations.size());
    }

    @Test
    void testGenerateDocumentValidationFailsForNestedDocumentStorageUrls() {
        Set<ConstraintViolation<GenerateDocument>> violations;
        final GenerateDocument.GenerateDocumentBuilder documentBuilder = GenerateDocument.builder()
                .client("client")
                .role("role")
                .guid("guid")
                .variables(new HashMap<>());

        // documentStorageUrls are missing
        violations = this.validator.validate(
                documentBuilder.documentStorageUrls(new ArrayList<>()).build()
        );
        Assertions.assertEquals(1, violations.size());

        // more than 1 documentStorageUrls and action is wrong
        final DocumentStorageUrl documentStorageUrl = DocumentStorageUrl.builder()
                .url("http://localhost:8080")
                .action("GET")
                .path("/path/to/file")
                .build();
        violations = this.validator.validate(
                documentBuilder.documentStorageUrls(List.of(documentStorageUrl, documentStorageUrl)).build()
        );
        Assertions.assertEquals(2, violations.size());
    }

}
