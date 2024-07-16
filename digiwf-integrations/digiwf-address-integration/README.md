# DigiWF Address Integration

The Address Integration connects the address service of the City of Munich to the DigiWF platform, allowing information
about addresses and streets to be retrieved.

## Usage of the Spring Boot Starter

The common description of all starters can be found in the
common [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/README.md#spring-boot-starter).

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

Set following environment variables to configure the service. Those are abbreviations of the properties above to shorten
the configuration in an environment like docker.

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
2. Run Stack using `docker-compose`

### Manual Test inside of München Network

1. Start the applications in the following order:
   1. EngineServiceApplication
      - Activate Spring profile `local,no-ldap,streaming`
      - Add Environment values from `stack/local-docker.env`
   2. DigiWFConnectorApplication
      - Activate Spring profile `local,streaming`
      - Add Environment values from `stack/local-docker.env`
   3. S3IntegrationApplication
      - Activate Spring profile `local,no-security`
      - Add Environment values from `stack/local-docker.env`
   4. TaskListApplication
      - Activate Spring profile `local,no-ldap,streaming`
      - Add Environment values from `stack/local-docker.env`
   5. DigiwfEmailIntegrationApplication
      - Activate Spring profile `local`
      - Add Environment values from `stack/local-docker.env`
2. Test the functionality with the
   process [example-email-V02](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/address-integration)
