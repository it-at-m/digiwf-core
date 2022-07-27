package io.muenchendigital.digiwf.json.serialization;


import io.muenchendigital.digiwf.json.serialization.serializer.JsonSerializerImpl;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonSchemaSerializationServiceTest {

    private JsonSerializationService jsonSchemaSerializationService;

    @BeforeEach
    private void setUp() {
        this.jsonSchemaSerializationService = new JsonSerializationService(new JsonSerializerImpl());
    }

    @Test
    public void serializeSimpleData() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/simpleSchema.json");

        final Map<String, Object> source = Map.of(
                "stringProp1", "stringValue",
                "numberProp1", 12
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        Assertions.assertThat(serializedData).isEqualTo(Map.of(
                "stringProp1", "stringValue"
        ));
    }

    @Test
    public void serializeData() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/schema.json");

        final Map<String, Object> source = Map.of(
                "stringProp1", "fsdafsda"
        );

        final Map<String, Object> target = Map.of(
                "dateprop", "20"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        final Map<String, Object> validData = new HashMap<>();

        validData.put("stringProp1", "fsdafsda");

        //override all
        Assertions.assertThat(serializedData).isEqualTo(validData);
    }


    @Test
    public void serializeDataAndUpdateWithReadonlyValues() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/simpleSchema.json");

        final Map<String, Object> source = Map.of(
                "stringProp1", "stringValue",
                "numberProp1", 12
        );

        final Map<String, Object> target = Map.of(
                "numberProp1", 100,
                "stringProp2", "100"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        final Map<String, Object> validData = new HashMap<>();

        validData.put("stringProp1", "stringValue");
        validData.put("numberProp1", 100);

        //override all
        Assertions.assertThat(serializedData).isEqualTo(validData);
    }

    @Test
    public void serializeCombinedSchemaData() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/schema.json");

        final Map<String, Object> source = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12
        );

        final Map<String, Object> target = Map.of(
                "numberProp1", 100
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        Assertions.assertThat(serializedData).isEqualTo(Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100
        ));
    }


    @Test
    public void serializeCombinedObjectSchemaData() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> source = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> target = Map.of(
                "numberProp1", 100
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        final Map<String, Object> erg = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test"
                ));

        Assertions.assertThat(new JSONObject(serializedData).toString()).isEqualTo(new JSONObject(erg).toString());
    }


    @Test
    public void filterObjectSchema() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> source = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "balbla", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        Assertions.assertThat(filteredData.toMap()).isEqualTo(Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        ));
    }

    @Test
    public void filterNullValues() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> data = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, data, true);

        Assertions.assertThat(filteredData.get("textarea1")).isEqualTo(null);
    }

    @Test
    public void mergeObjectData() {

        final Map<String, Object> source = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> target = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test1",
                        "stringProp2", "test2"
                )
        );

        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(new JSONObject(source), new JSONObject(target));

        Assertions.assertThat(mergedData).isEqualTo(Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test2"
                )
        ));
    }

    @Test
    public void filterAndMergeObjectData() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> data = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "stringProp5", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, data, true);

        final Map<String, Object> target = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test1",
                        "stringProp2", "test2"
                )
        );

        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        Assertions.assertThat(mergedData).isEqualTo(Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test2"
                )
        ));
    }

    @Test
    public void serializeCombinedObjectSchemaDataWithPreviousData() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> source = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> target = Map.of(
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp2", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(target));

        final Map<String, Object> erg = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test"
                ));

        Assertions.assertThat(new JSONObject(serializedData).toString()).isEqualTo(new JSONObject(erg).toString());
    }


    @Test
    public void serializeCustomTypes() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/customTypesSchema.json");

        final Map<String, Object> source = Map.of(
                "FormField_Grusstext", "meinValue"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        Assertions.assertThat(serializedData).isEqualTo(Map.of(
                "FormField_Grusstext", "meinValue"
        ));
    }

    @Test
    public void serializeComplexObjectStructure() throws URISyntaxException, IOException {
        final Map<String, Object> source = Map.of(
                "textarea", "100",
                "textfeld", "100",
                "objekt1", Map.of(
                        "objektTextfeld", "fdsfsdafsdafadsfsadfsdafd",
                        "objektSchalter", true)
        );

        final String rawSchema = this.getSchemaString("/schema/validation/complexObjectSchema.json");

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        final Map<String, Object> erg = Map.of(
                "textarea", "100",
                "textfeld", "100",
                "objekt1", Map.of(
                        "objektTextfeld", "fdsfsdafsdafadsfsadfsdafd",
                        "objektSchalter", true));

        Assertions.assertThat(this.areEqual(erg, serializedData)).isEqualTo(true);
    }

    @Test
    public void checkConditionalSubSchema() throws URISyntaxException, IOException {
        final Map<String, Object> source = Map.of(
                "stringProp1", "100"
        );

        final String rawSchema = this.getSchemaString("/schema/validation/conditionalSubSchema.json");

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);
        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        final Map<String, Object> erg = Map.of(
                "stringProp1", "100");

        Assertions.assertThat(this.areEqual(erg, serializedData)).isEqualTo(true);
    }

    @Test
    public void generateObjectStructure() {
        final JSONObject object = this.jsonSchemaSerializationService.generateValue("#/antragsdaten/datumAntragstellung/stringProp1", "testValue");
        assertEquals(object.toString(), "{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp1\":\"testValue\"}}}");
    }

    @Test
    public void extractValue() {
        final JSONObject object = new JSONObject("{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp1\":\"testValue\"}}}");
        final Object value = this.jsonSchemaSerializationService.extractValue(object.toMap(), "#/antragsdaten/datumAntragstellung/stringProp1");
        assertEquals(value, "testValue");
    }

    @Test
    public void extractValueByKey() {
        final JSONObject object = new JSONObject("{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp2\":\"testValue\"}}}");
        final Object value = this.jsonSchemaSerializationService.extractValue(object.toMap(), "#/antragsdaten/datumAntragstellung/stringProp1");
        assertNull(value);
    }

    @Test
    public void extract() {
        final JSONObject object = new JSONObject("{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp2\":\"testValue\"}}}");
        final Object value = this.jsonSchemaSerializationService.extractValue(object.toMap(), "#/antragsdaten/datumAntragstellung/stringProp1");
        assertNull(value);
    }

    @Test
    public void initalizeAndMergeObjectSchema() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> newData = Map.of(
                "booleanprop", true,
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "abc",
                        "textarea1", "xyz"
                )
        );

        final JSONObject initializedObject = this.jsonSchemaSerializationService.initialize(rawSchema);
        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(new JSONObject(newData), initializedObject);

        Assertions.assertThat(mergedData).isEqualTo(Map.of(
                "booleanprop", true,
                "dateprop", "",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "abc",
                        "textarea1", "xyz"
                ),
                "stringProp1", "",
                "textarea1", ""
        ));
    }

    @Test
    public void initializeSimpleSchema() throws URISyntaxException, IOException {
        final String rawSchema = this.getSchemaString("/schema/serialization/simpleSchema.json");

        final JSONObject initializedObject = this.jsonSchemaSerializationService.initialize(rawSchema);

        Assertions.assertThat(initializedObject.toMap()).isEqualTo(Map.of(
                "numberProp1", "",
                "stringProp1", "",
                "stringProp2", ""
        ));
    }

    //------------------------------------ Helper Methods ------------------------------------//

    private String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI())));
    }

    private boolean areEqual(final Map<String, Object> first, final Map<String, Object> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet().stream()
                .allMatch(e -> this.compareObject(e.getValue(), second.get(e.getKey())));
    }

    private boolean compareObject(final Object obj1, final Object obj2) {
        if (obj1 instanceof JSONObject) {
            return obj1.toString().equals(obj2.toString());
        }

        return obj1.equals(obj2);
    }
}
