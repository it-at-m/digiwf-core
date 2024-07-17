# Digiwf CoSys Integration

The CoSys Integration connects the CoSys Service with the DigiWF platform, allowing the filling of forms stored in CoSys
and subsequently saving them in S3 storage.

## Usage of the Spring Boot Starter

The common description of all starters can be found in the common [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/README.md#spring-boot-starter).

You can use the following configurations for the DigiWF CoSys integration:

| Property                                               | Description                                                            |
|--------------------------------------------------------|------------------------------------------------------------------------|
| `io.muenchendigital.digiwf.cosys.url`                  | Url of the CoSys service                                               |
| `io.muenchendigital.digiwf.cosys.merge.datafile`       |                                                                        |
| `io.muenchendigital.digiwf.cosys.merge.inputLanguage`  | incoming language                                                      |
| `io.muenchendigital.digiwf.cosys.merge.outputLanguage` | outgoing language                                                      |
| `io.muenchendigital.digiwf.cosys.merge.keepFields`     |                                                                        |
| `digiwf.s3.client.document-storage-url`                | Document storage url                                                   |
| `digiwf.s3.client.max-file-size`                       | Max allowed file size to save in the S3 storage                        |
| `digiwf.s3.client.enable-security`                     | flag enables or disables security for the connection to the S3 storage |

If you set `digiwf.s3.client.enable-security` to `true`, you need to configure security settings for the connection to
the S3 storage.

| Property                                                                 | Description                                                          |
|--------------------------------------------------------------------------|----------------------------------------------------------------------|
| `spring.security.oauth2.client.provider.keycloak.issuer-uri`             | address of the issuer of JSON Web Tokens (JWTs) in Keycloak          |
| `spring.security.oauth2.client.provider.keycloak.user-info-uri`          | URL in Keycloak for retrieving user profile information              |
| `spring.security.oauth2.client.provider.keycloak.jwk-set-uri`            | location of the JSON Web Key Set (JWK Set) in Keycloak               |
| `spring.security.oauth2.client.provider.keycloak.user-name-attribute`    | attribute name in the JWT that holds the username, i. e. `user_name` |
| `spring.security.oauth2.client.registration.s3.provider`                 | provider specification, i.e. `keycloak`                              |
| `spring.security.oauth2.client.registration.s3.authorization-grant-type` | Authorization grant type i.e. `client_credentials`                   |
| `spring.security.oauth2.client.registration.s3.client-id`                | SSO client id used for sso connection to the S3 storage              |
| `spring.security.oauth2.client.registration.s3.client-secret`            | SSO secret id used for SSO connection to the s3 storage              |
| `spring.security.oauth2.client.registration.s3.scope`                    | list of scopes requested from Keycloak                               |

To connect to the CoSys service, you need to set also the following properties:

| Property                                                                    | Description                                         |
|-----------------------------------------------------------------------------|-----------------------------------------------------|
| `spring.security.oauth2.client.registration.cosys.authorization-grant-type` | Authorization grant type i. e. `client_credentials` |
| `spring.security.oauth2.client.registration.cosys.client-id`                | SSO client id used for cosys sso connection         |
| `spring.security.oauth2.client.registration.cosys.client-secret`            | SSO secret id used for cosysy SSO connection        |
| `spring.security.oauth2.client.provider.cosys.token-uri`                    | Url for authenticating to cosys                     |

To use the streaming adapter, you need to set the properties as described in
the [DigiWF Message library](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/README.md#configuration).

## Run the Digiwf CoSys Integration Service

To run the service i.e. in Open Shift you can set the environment variables below or run the service locally as
described in the [Getting Started](#getting-started) section.

### Configuration with environment variables

Set following environment variables to configure the service. Those are abbreviations of the properties above to shorten
the configuration in an environment like docker.

| Environment Variable                                                        | Description                                                                                     |
|-----------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| `IO_MUENCHENDIGITAL_DIGIWF_COSYS_URL`                                       | Host url and endpoint of the CoSys service                                                      |
| `DIGIWF_ENV`                                                                | Environment in which the service runs                                                           |
| `COSYS_INTEGRATION_SERVER_PORT`                                             | Port of the application                                                                         |
| `KAFKA_SECURITY_PROTOCOL`                                                   | Security protocol of kafka (default is PLAINTEXT)                                               |
| `KAFKA_BOOTSTRAP_SERVER`                                                    | Kafka server address (default is localhost)                                                     |
| `KAFKA_BOOTSTRAP_SERVER_PORT`                                               | Kafka server port (default is 29092)                                                            |
| `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_COSYS_CLIENT-ID`                | SSO client id used for cosys sso connection.                                                    |
| `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_COSYS_CLIENT-SECRET`            | SSO secret id used for SSO connection.                                                          |
| `SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_COSYS_TOKEN-URI`                    | Url for authenticating to cosys                                                                 |
| `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_COSYS_AUTHORIZATION-GRANT-TYPE` | Authorization grant type i.e. `client_credentials`                                              |
| `S3_MAX_FILE_SIZE`                                                          | Max allowed file size to save in the S3 storage                                                 |
| `DIGIWF_S3_CLIENT_DOCUMENTSTORAGEURL`                                       | Url of the S3 storage service                                                                   |
| `SSO_S3_CLIENT_SECRET`                                                      | SSO client id used for sso connection to the S3 storage                                         |
| `SSO_S3_CLIENT_ID`                                                          | SSO secret id used for sso connection to the S3 storage                                         |
| `SSO_ISSUER_URL`                                                            | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}` |
| `SSO_BASE_URL`                                                              | Base url used for sso connection                                                                |
| `SSO_REALM`                                                                 | Realm used for sso connection                                                                   |

### Getting started

The following steps are needed to run the integration locally.

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`
3. Open Minio (http://localhost:9000, user: `minio`, pass: `Test1234`) and create a Bucket.
4. Store the values in `S3_BUCKETNAME`, `S3_ACCESSKEY` und `S3_SECRETKEY`of `stack/local-docker.env`
5. Set the following security configurations
    - spring.security.oauth2.client.registration.cosys.authorization-grant-type=client_credentials
    - spring.security.oauth2.client.registration.cosys.client-id=xx
    - spring.security.oauth2.client.registration.cosys.client-secret=xxx
    - spring.security.oauth2.client.provider.cosys.token-uri=xxx
6. Set the following cosys configuration
    - io.muenchendigital.digiwf.cosys.url=xxx

### Testing functionality

1. Start DigiwfCoSysExampleApplication
    - Activate Spring profile `local,streaming`
    - Add Environment values from `stack/local-docker.env`

2. Execute the [Example](#digiwf-cosys-integration-example) application and try the integrations features out.

### Testing with DigiWF

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
    5. DigiwfCoSysApplication
        - Activate Spring profile `local,streaming`
        - Add Environment values from `stack/local-docker.env`
2. Test the functionality with
   process [Example CoSys GenerateDocument (Streaming)](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/cosys-integration)

