# DigiWF JSON Serialization examples

This example app has the `SerializationController` which injects the `JsonSchemaSerializationService`.
The 2 endpoints `/serialize` and `/deserialize` call the serialize and deserialize methods of the `JsonSchemaSerializationService`.

## Serialization Examples

Example requests are also available under [example-requests/serialize-example-requests.http](example-requests/serialize-example-requests.http).

**Serialize simple data**

Serialize example for simple data structure with 2 properties. 

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/simpleSchema.json\",
          \"data\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"12\"
          },
          \"previousData\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"12\"
          }
        }"
```

---

**serialize with readonly property**

Serialize data with the readonly property *numberProp1*. The request below returns for *numberProp1* **7**.

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/simpleSchema.json\",
          \"data\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"12\"
          },
          \"previousData\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"7\"
          }
        }"
```

---

**serialize data with previous data**

Previous data is added to data in the serialization process.

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/schema.json\",
          \"data\": {
            \"stringProp1\": \"fsdafsda\"
          },
          \"previousData\": {
            \"dateprop\": \"20\"
          }
        }"
```

---

**serialize combined object schema data**

Serialize example with a combined object schema. 

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/objectSchema.json\",
          \"data\": {
            \"textarea1\": \"textAreaValue\",
            \"booleanprop\": true,
            \"dateprop\": \"2020-10-1\",
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": 12,
            \"objectProp\": {
              \"stringProp1\": \"test\"
            }
          },
          \"previousData\": {
            \"numberProp1\": 100
          }
        }"
```

---

**serialize custom types**

Serialize example with custom types. Custom types are specified in the json schema.

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/customTypesSchema.json\",
          \"data\": {
            \"FormField_Grusstext\": \"meinValue\"
          },
          \"previousData\": {
        
          }
        }"
```

---

**serialize complex object structure**

Serialize example with more complex object structure.

```
curl -X POST --location "http://localhost:8080/serialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/validation/complexObjectSchema.json\",
          \"data\": {
            \"textarea\": \"100\",
            \"textfeld\": \"100\",
            \"objekt1\": {
              \"objektTextfeld\": \"fdsfsdafsdafadsfsadfsdafd\",
              \"objektSchalter\": true
            }
          },
          \"previousData\": {
        
          }
        }"
```

---

## Deserialization Examples

Example requests are also available under [example-requests/deserialize-example-requests.http](example-requests/deserialize-example-requests.http).

**Simple deserialize example**

A very simple deserialize example with 2 properties.

````
curl -X POST --location "http://localhost:8080/deserialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/simpleSchema.json\",
          \"data\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"12\"
          }
        }"
````

---

**Deserialize example with to many data**

By sending the following request to the example it returns only the properties *stringProp1*, *numberProp1* and *stringProp2* which are specified in the json schema *simpleSchema.json*.

```
curl -X POST --location "http://localhost:8080/deserialize" \
    -H "Content-Type: application/json" \
    -d "{
          \"schema\": \"/schema/serialization/simpleSchema.json\",
          \"data\": {
            \"stringProp1\": \"stringValue\",
            \"numberProp1\": \"12\",
            \"stringProp2\": \"stringValue2\",
            \"nonExistingStringProp1\": \"nonExistingStringProp1\",
            \"nonExistingStringProp2\": \"nonExistingStringProp2\",
            \"nonExistingStringProp3\": \"nonExistingStringProp3\"
          }
        }"
```

---
