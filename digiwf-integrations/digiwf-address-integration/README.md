# DigiWF Address Integration

The Address Integration connects the address service of the City of Munich to the DigiWF platform, allowing information
about addresses and streets to be retrieved.

## Usage of the Spring Boot Starter

The DigiWF Address Integration is provided as a Spring Boot Starter project. It was implemented in a hexagonal
architecture to ensure adaptability and extensibility.

You can integrate the `digiwf-address-integration-starter` into your project as follows:

**With Maven**

```xml

<dependency>
    <groupId>de.muenchen.oss.digiwf</groupId>
    <artifactId>digiwf-address-integration-starter</artifactId>
    <version>${digiwf.version}</version>
</dependency>
```

**With Gradle**

```gradle
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-address-integration-starter', version: '${digiwf.version}'
```

To extend or replace the functions of the integration, you only need to override the port interfaces and provide them
as `@Bean`. This will replace our standard implementation with your custom implementation.

You can find the port definitions at the
path: [digiwf-address-integration-core/src/main/java/de/muenchen/oss/digiwf/address/integration/application/port](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-address-integration/digiwf-address-integration-core/src/main/java/de/muenchen/oss/digiwf/address/integration/application/port).

The Address Client is responsible for communication with the City of Munich's Address Service. The client was
implemented as a library in its own Maven module to encapsulate dependencies. The implementation of the client can also
be extended or replaced by implementing the API interfaces AddressGermanyApi, AddressMunichApi, and StreetsMunichApi.
These interfaces must also be provided as `@Bean`.

You can find the API interfaces at the
path: [digiwf-address-integration-client/src/main/java/de/muenchen/oss/digiwf/address/integration/client/api](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-address-integration/digiwf-address-integration-client/src/main/java/de/muenchen/oss/digiwf/address/integration/client/api)

You can use the following configurations for the DigiWF Address Integration:

| Property                                     | Description                |
|----------------------------------------------|----------------------------|
| `de.muenchen.oss.digiwf.address.service.url` | Url of the address service |

To use the streaming adapter, you need to set the properties as described in
the [DigiWF Message library](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/README.md#configuration).

## Run the DigiWF Address Integration Service

To run the service i.e. in Open Shift you can set the environment variables below or run the service locally as
described in the [Getting Started](#getting-started) section.

### Service Configuration with environment variables

Set following environment variables to configure the service.

| Environment variable              | Description                                                       |
|-----------------------------------|-------------------------------------------------------------------|
| `DIGIWF_ENV`                      | Environment in which the service runs                             |
| `ADDRESS_SERVICE_URL`             | URL of the address service                                        |
| `ADDRESS_INTEGRATION_SERVER_PORT` | Port number of this address integration service (default is 8080) |
| `KAFKA_SECURITY_PROTOCOL`         | Security protocol of kafka (default is PLAINTEXT)                 |
| `KAFKA_BOOTSTRAP_SERVER`          | Kafka server address (default is localhost)                       |
| `KAFKA_BOOTSTRAP_SERVER_PORT`     | Kafka server port (default is 29092)                              |

### Getting started

The following steps are needed to run the integration locally.

1. Build it with `mvn clean install`
2. Execute the e2e
   test [AddressIntegrationE2eTest.java](digiwf-address-integration-service/src/test/java/de/muenchen/oss/digiwf/address/integration/AddressIntegrationE2eTest.java)

### Testing functionality

The [AddressIntegrationE2eTest.java](digiwf-address-integration-service/src/test/java/de/muenchen/oss/digiwf/address/integration/AddressIntegrationE2eTest.java)
is a test of the integrations functionality using an embedded kafka instance and wiremock to mock the api to the
Address-Service.
