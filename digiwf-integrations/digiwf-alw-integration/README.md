# Digiwf ALW Integration

Provides integration to ALW (Ausländerwesen) service for retrieval of responsible employees.

## Configuration

Set following environment variables to configure the service.

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
| `KAFKA_BOOTSTRAP_SERVER`        | Kafka server address (default is localhost)                                                        |
| `KAFKA_BOOTSTRAP_SERVER_PORT`   | Kafka server port (default is 29092)                                                               |
| `SSO_ISSUER_URL`                | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}`    |
| `SSO_BASE_URL`                  | Base url used for sso connection.                                                                  |
| `SSO_REALM`                     | Realm used for sso connection.                                                                     |
| `DIGIWF_SECURITY_CLIENT-ID`     | SSO client id used for sso connection.                                                             |
| `DIGIWF_SECURITY_CLIENT-SECRET` | SSO secret id used for sso connection.                                                             |

## Getting started

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`

## Request

For the valid request an AZR number must be provided. This number must contain 12-digits. If the AZR number
is missing or has a wrong format a VALIDATION_ERROR is thrown.

## Manual Test outside of München Network

1. Start the `docker-compose` setup
2. Start application with profiles `local` and `alw-emulation`
3. Use `digiwf-alw-integration-service/rest-api-client/example.http`

## Manual Test inside of München Network

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

## Set up for use of digiwf-alw-integration-starter

Follow these steps to use the starter in your application:

1. Use the Spring Initializr and create a Spring Boot application with `Spring Web`
   dependencies. [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-alw-integration-starter dependency.

With Maven:

``` xml
   <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-alw-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

``` groovy
implementation group: 'de.muenchen.oss.digiwf', name: 'digiwf-alw-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use Kafka.

Maven:

 ``` xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

Gradle:

``` groovy
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-alw-integration-starter. You also have to configure the
   topics you want to read/send messages from/to.

5. Configure these items for your event bus:

``` properties
spring.cloud.stream.bindings.sendMessage-out-0.destination: <YOUR CUSTOM REQUEST TOPIC>
spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination: <YOUR CUSTOM RESPONSE TOPIC>
spring.cloud.stream.bindings.functionRouter-in-0.group: <YOUR GROUP>
spring.cloud.stream.bindings.functionRouter-in-0.destination: <YOUR CUSTOM REQUEST TOPIC> # For a roundtrip use the same value as in "spring.cloud.stream.bindings.sendMessage-out-0.destination" 
```

6. Configure details of your ALW System:

``` yaml
digiwf.alw.personeninfo:
  base-url: <YOUR ALW SYSTEM URL>
  rest-endpoint: <YOUR PERSONENINFO ENDPOINT>
  timeout: <YOUR CONNECTION TIMEOUT>
  username: <YOUR BASIC AUTH USER>
  password: <YOUR BASIC AUTH PASSWORD>
  functional-ping:
    enabled: true
    azr-number: <YOUR SAMPLE AZR NUMBER>
```

7. Define a map as a named resource bean (see **BEAN_ALW_SACHBEARBEITUNG**
   of <i>[SachbearbeitungMapperConfig](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/digiwf-alw-integration/digiwf-alw-integration-core/src/main/java/io/muenchendigital/digiwf/alw/integration/configuration/SachbearbeitungMapperConfig.java) </i> )
   to support mapping of the ALW System responses to directory-ous.