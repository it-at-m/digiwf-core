package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verify bean validation of {@link GenerateDocument}
 *
 * @author ext.dl.moesle
 */
public class GenerateDocumentPresignedUrlsDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testGenerateDocumentValidationSuccess() {
        List.of("POST", "PUT").forEach(action -> {
            final DocumentStorageUrl validDocumentStorageUrl = DocumentStorageUrl.builder()
                    .url("http://localhost:8080")
                    .action(action)
                    .path("/path/to/file")
                    .build();
            final GenerateDocumentPresignedUrlsDTO validDocument = new GenerateDocumentPresignedUrlsDTO(
                    "client",
                    "role",
                    "guid",
                    jsonNode(),
                    List.of(validDocumentStorageUrl));
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

        // documentStorageUrls are missing
        violations = this.validator.validate(
                new GenerateDocumentPresignedUrlsDTO(
                        "client",
                        "role",
                        "guid",
                        jsonNode(),
                        new ArrayList<>())
        );
        Assertions.assertEquals(1, violations.size());

        // more than 1 documentStorageUrls and action is wrong
        final DocumentStorageUrl documentStorageUrl = DocumentStorageUrl.builder()
                .url("http://localhost:8080")
                .action("GET")
                .path("/path/to/file")
                .build();
        violations = this.validator.validate(
                new GenerateDocumentPresignedUrlsDTO(
                        "client",
                        "role",
                        "guid",
                        jsonNode(),
                        List.of(documentStorageUrl, documentStorageUrl))
        );
        Assertions.assertEquals(2, violations.size());
    }


    @Test
    void testJsonNodeVariableString() {

        JsonNode jsonNode = jsonNode();

        String variables = "{\n" +
                "    \"name\" : \"John\",\n" +
                "    \"age\" : 30,\n" +
                "    \"address\" : {\n" +
                "        \"street\" : \"MainSt\",\n" +
                "        \"city\" : \"Anytown\",\n" +
                "        \"zip\" : \"12345\"\n" +
                "    }\n" +
                "}";

        assertThat(jsonNode.toString()).isEqualTo(variables.replace(" ", "").replace("\n", ""));
    }


    private JsonNode jsonNode() {
        String jsonString = "{\n" +
                "    \"name\": \"John\",\n" +
                "    \"age\": 30,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"MainSt\",\n" +
                "        \"city\": \"Anytown\",\n" +
                "        \"zip\": \"12345\"\n" +
                "    }\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
