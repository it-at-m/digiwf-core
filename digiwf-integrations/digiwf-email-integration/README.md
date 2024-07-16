# DigiWF Email Integration

The DigiWF E-Mail integration allows users to send emails via the DigiWF platform. Process developers can use this
integration to incorporate email communication into their BPMN processes.

## Usage of the Spring Boot Starter

The common description of all starters can be found in the
common [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/README.md#spring-boot-starter).

You can use the following configurations for the DigiWF E-Mail integration:

| Property                                                      | Description                                                                         |
|---------------------------------------------------------------|-------------------------------------------------------------------------------------|
| `io.muenchendigital.digiwf.mail.fromAddress`                  | sender address used for all emails sent via the DigiWF E-Mail integration           |
| `io.muenchendigital.digiwf.mail.defaultReplyToAddress`        | default Reply-To email address for technical emails that should not be responded to |
| `io.muenchendigital.digiwf.mail.metrics.totalMailCounterName` | name of the Micrometer counter that counts the number of sent emails                |
| `io.muenchendigital.digiwf.mail.metrics.failureCounterName`   | name of the Micrometer counter that counts the number of failed emails              |
| `spring.mail.host`                                            | server host address                                                                 |
| `spring.mail.port`                                            | server port                                                                         |
| `spring.mail.username`                                        | username for the mail server authentication                                         |
| `spring.mail.password`                                        | password for the mail server authentication                                         |
| `spring.mail.properties.mail.tls`                             | specifies whether TLS is enabled for the email connection                           |
| `spring.mail.properties.mail.transport.protocol`              | email transport protocol to be used, i. e. `smtp`                                   |
| `spring.mail.properties.mail.smtp.host`                       | SMTP server host address                                                            |
| `spring.mail.properties.mail.smtp.port`                       | SMTP server port                                                                    |
| `spring.mail.properties.mail.smtp.auth`                       | specifies whether authentication is required for the SMTP server                    |
| `spring.mail.properties.mail.smtp.starttls.enable`            | specifies whether STARTTLS is enabled for the SMTP server to secure the connection  |
| `digiwf.s3.client.document-storage-url`                       | document storage url                                                                |
| `digiwf.s3.client.enable-security`                            | flag enables or disables security for the connection to the S3 storage              |

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

To use the streaming adapter, you need to set the properties as described in
the [DigiWF Message library](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/README.md#configuration).

## Run the Digiwf E-Mail Integration Service## Run the Digiwf CoSys Integration Service

To run the service i.e. in Open Shift you can set the environment variables below or run the service locally as
described in the [Getting Started](#getting-started) section.

### Configuration with environment variables

Set following environment variables to configure the service. Those are abbreviations of the properties above to shorten
the configuration in an environment like docker.

| Environment Variable                         | Description                                                                                     |
|----------------------------------------------|-------------------------------------------------------------------------------------------------|
| `MAIL_HOST`                                  | server host address                                                                             |
| `MAIL_PORT`                                  | server port                                                                                     |
| `MAIL_USERNAME`                              | username for the mail server authentication                                                     |
| `MAIL_PASSWORD`                              | password for the mail server authentication                                                     |
| `SPRING_MAIL_USERNAME`                       | username for the mail server authentication                                                     |
| `IO_MUENCHENDIGITAL_DIGIWF_MAIL_FROMADDRESS` | sender address used for all emails sent via the DigiWF E-Mail integration                       |
| `KAFKA_SECURITY_PROTOCOL`                    | Security protocol of kafka (default is PLAINTEXT)                                               |
| `KAFKA_BOOTSTRAP_SERVER`                     | Kafka server alw (default is localhost)                                                         |
| `KAFKA_BOOTSTRAP_SERVER_PORT`                | Kafka server port (default is 29092)                                                            |
| `SSO_ISSUER_URL`                             | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}` |
| `SSO_BASE_URL`                               | Base url used for sso connection.                                                               |
| `SSO_REALM`                                  | Realm used for sso connection.                                                                  |
| `DIGIWF_S3_CLIENT_DOCUMENTSTORAGEURL`        | Url of the S3 storage service                                                                   |
| `SSO_S3_CLIENT_SECRET`                       | SSO client id used for sso connection to the S3 storage                                         |
| `SSO_S3_CLIENT_ID`                           | SSO secret id used for sso connection to the S3 storage                                         |

### Getting Started

1. Build it with `mvn clean install`
2. Run the Stack using `docker-compose`
3. Run the Email Integration ([digiwf-email-integration-service](digiwf-email-integration-service)
   or [digiwf-email-integration-example](digiwf-email-integration-example))

## Testing functionality

1. Start the DigiwfEmailExampleApplication
2. Run the [example http requests](digiwf-email-integration-example/src/main/resources/rest-api-client/example.http) in
   IntelliJ
3. Check Mailhog (http://localhost:9025) for the received emails

## Testing with DigiWF

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
        - Add Environment values from `stack/local-docker.env`
2. Test the functionality with the
   process [example-email-V02](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-integration/email-example-V02)
