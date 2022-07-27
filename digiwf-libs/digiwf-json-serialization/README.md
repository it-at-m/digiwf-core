<div id="top"></div>


<h3 align="center">DigiWF JSON Serialization</h3>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

DigiWF JSON Serialization serializes and deserializes data based on json schemas.

**Features**

* **Serialize:** The serialize function creates a json object based on the json schema and the data you provide (current and previous data). The current data is used if the property is not readonly or null. Otherwise, the previous data is added to data in the serialization process.
* **Deserialize:** The deserialize functionality takes a json object and returns the relevant parts of it based on the json schema.

At the moment only JSON schema [Draft v7](https://json-schema.org/draft-07/json-schema-release-notes.html) is supported.

<p align="right">(<a href="#top">back to top</a>)</p>


### Built With

This project is built with:

* [Spring Boot](https://spring.io/projects/spring-boot)
* [everit-json-schema](https://github.com/everit-org/json-schema)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

_Below is an example of how you can installing and setup up your service_

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-json-serialization dependency

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-json-serialization-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-json-serialization-starter', version: '${digiwf.version}'
```

3. Inject the `JsonSchemaSerializationService` in your application


<!-- USAGE EXAMPLES -->
## Usage

The library has several functionalities that can be configured. We have provided examples that show how you can use
them.

_For more examples, please refer to the [Examples](example) folder_

First inject the `JsonSchemaSerializationService` in your class. The `JsonSchemaSerializationService` is a 
wrapper around a serializer instance which provides serialize and deserialize methods. 

```java
@RequiredArgsConstructor
public class YourClass {
    // inject JsonSchemaSerializationService
    private final JsonSchemaSerializationService jsonSchemaSerializationService;
}
```

Then you can call `jsonSchemaSerializationService.serialize(schema, data, previousData)` to serialize data based on
the json schema you are providing.
To deserialize data you can call `jsonSchemaSerializationService.deserialize(schema, data)`.

### Create a custom serializer

If you want to use a custom serializer create a serializer which implements the `JsonSchemaBaseSerializer` 
and create a bean that provides your custom serializer in the configuration.
Then your custom serializer is used in the `JsonSchemaSerializationService` instead of the default one.

```java
public class MyCustomJsonSchemaSerializer implements JsonSchemaBaseSerializer {
   @Override
   public Map<String, Object> serialize(Schema schema, JSONObject data, JSONObject previousData) {
      return null;
   }

   @Override
   public Map<String, Object> deserialize(Schema schema, Map<String, Object> data) {
      return null;
   }
}
```

```java
@Configuration
public class MyJsonSerializationAutoConfiguration {
   @Bean
   public JsonSchemaBaseSerializer customJsonSchemaSerializer() {
      return new MyCustomJsonSchemaSerializer();
   }    
}
```


