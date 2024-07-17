# Digiwf ALW Integration

Provides integration to ALW (Ausländerwesen) service for retrieval of responsible employees.

## Usage of the Spring Boot Starter

The common description of all starters can be found in the
common [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/README.md#spring-boot-starter).
You can use the following configurations for the DigiWF ALW Integration:

| Property                                             | Description                                                                   |
|------------------------------------------------------|-------------------------------------------------------------------------------|
| `digiwf.alw.personeninfo.base-url`                   | Host url of the alw service                                                   |
| `digiwf.alw.personeninfo.rest-endpoint`              | endpoint url of the alw service                                               |
| `digiwf.alw.personeninfo.timeout`                    | Timeout of the request to the alw endpoint (default is 1500)                  |
| `digiwf.alw.personeninfo.username`                   | username                                                                      |
| `digiwf.alw.personeninfo.password`                   | password                                                                      |
| `digiwf.alw.personeninfo.functional-ping.enabled`    | Enables functional pinging on startup (default is `true`)                     |
| `digiwf.alw.personeninfo.functional-ping.azr-number` | AZR number used in functional pinging on startup (default is an empty string) |

To use the streaming adapter, you need to set the properties as described in
the [DigiWF Message library](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/README.md#configuration).

The authorization is outsourced to the digiwf-spring-security-starter. You also need to set the properties as described
in
the [DigiWF Spring Security library](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-security/README.md).

You will also have to define a map as a named resource bean (see **BEAN_ALW_SACHBEARBEITUNG**
of <i>[SachbearbeitungMapperResourceConfig](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-alw-integration/digiwf-alw-integration-service/src/main/java/de/muenchen/oss/digiwf/alw/integration/configuration/SachbearbeitungMapperResourceConfig.java) </i> )
to support mapping of the ALW System responses to directory-ous.

## Run the DigiWF ALW Integration Service

To run the service i.e. in Open Shift you can set the environment variables below or run the service locally as
described in the [Getting Started](#getting-started) section.

### Service Configuration with environment variables

Set following environment variables to configure the service. Those are abbreviations of the properties above to shorten
the configuration in an environment like docker.

| Environment Variable            | Description                                                                                        |
|---------------------------------|----------------------------------------------------------------------------------------------------|
| `ALW_REST_BASE_URL`             | Host url of the alw service                                                                        |
| `ALW_REST_ENDPOINT`             | endpoint url of the alw service                                                                    |
| `ALW_REST_USERNAME`             | username                                                                                           |
| `ALW_REST_SECRET`               | password                                                                                           |
| `ALW_SACHBEARBEITUNG_CONFIG`    | location of the responsibility mapping table (default is `/config/alw-sachbearbeitung.properties`) |
| `ALW_TIMEOUT`                   | Timeout of the request to the alw endpoint (default is 1500)                                       |
| `ALW_PING_ENABLED`              | Enables functional pinging on startup (default is `true`)                                          |
| `ALW_PING_AZR_NUMBER`           | AZR number used in functional pinging on startup (default is an empty string)                      |
| `DIGIWF_ENV`                    | Environment in which the service runs                                                              |
| `ALW_INTEGRATION_SERVER_PORT`   | Port of the application                                                                            |
| `KAFKA_SECURITY_PROTOCOL`       | Security protocol of kafka (default is PLAINTEXT)                                                  |
| `KAFKA_BOOTSTRAP_SERVER`        | Kafka server alw (default is localhost)                                                            |
| `KAFKA_BOOTSTRAP_SERVER_PORT`   | Kafka server port (default is 29092)                                                               |
| `SSO_ISSUER_URL`                | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}`    |
| `SSO_BASE_URL`                  | Base url used for sso connection.                                                                  |
| `SSO_REALM`                     | Realm used for sso connection.                                                                     |
| `DIGIWF_SECURITY_CLIENT-ID`     | SSO client id used for sso connection.                                                             |
| `DIGIWF_SECURITY_CLIENT-SECRET` | SSO secret id used for sso connection.                                                             |

### Getting started

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`

### Request

For the valid request an AZR number must be provided. This number must contain 12-digits. If the AZR number
is missing or has a wrong format a VALIDATION_ERROR is thrown.

### Manual Test outside of München Network

1. Start the `docker-compose` setup
2. Start application with profiles `local` and `alw-emulation`
3. Use `digiwf-alw-integration-service/rest-api-client/example.http`

### Manual Test inside of München Network

1. Set Spring Properties:
    ```
   io:
     muenchendigital:
       digiwf:
         alw:
           personeninfo:
             sachbearbeitung-config-url: /config/alw-sachbearbeitung.properties
           baseurl: <host>
           restendpoint: <endpoint url>
           timeout: 15000
           username: <username>
           password: <password>
   ```
2. Start the applications in the following order:
    1. EngineServiceApplication
        - Activate Spring profile `local,no-ldap,streaming`
        - Add Environment values from `stack/local-docker.env`
    2. DigiWFConnectorApplication
        - Activate Spring profile `local,streaming`
        - Add Environment values from `stack/local-docker.env`
    4. TaskListApplication
        - Activate Spring profile `local,no-ldap,streaming`
        - Add Environment values from `stack/local-docker.env`
    5. AlwServiceApplication
        - Activate Spring profile `local`
        - Add Environment values from `stack/local-docker.env`
3. Test the functionality with the
   process [alw-integration](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/alw-integration)
