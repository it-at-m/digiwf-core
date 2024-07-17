# Digiwf DMS Integration

The DigiWF DMS Integration connects the document management system with DigiWF, allowing the uploading and downloading
of documents to and from S3 storage.

## Usage of the Spring Boot Starter

The common description of all starters can be found in the
common [README](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-integrations/README.md#spring-boot-starter).

You can use the following configurations for the DigiWF Cosys integration:

| Property                                               | Description                                                           |
|--------------------------------------------------------|-----------------------------------------------------------------------|
| `digiwf.integration.dms.fabasoft.businessapp`          | App registered in the DMS                                             |
| `digiwf.integration.dms.fabasoft.username`             | Technical fabasoft dms user                                           |
| `digiwf.integration.dms.fabasoft.password`             | Technical fabasoft dms password                                       |
| `digiwf.integration.dms.fabasoft.address`              | Fabasoft url                                                          |
| `digiwf.integration.dms.fabasoft.uiurl`                | Url to fabasoft web interface                                         |
| `digiwf.integration.dms.fabasoft.enable-MTOM`          | Flag to enable message transmission optimization                      |
| `digiwf.s3.client.document-storage-url`                | Url of the S3 integration service                                     |
| `digiwf.s3.client.enable-security`                     | Flag to enable security                                               |
| `digiwf.s3.client.max-file-size`                       | Maximum allowed file size that can be downloaded from S3              |
| `digiwf.s3.client.max-batch-size`                      | Maximum allowed total size of files that can be processed at one time |
| `de.muenchen.oss.digiwf.dms.supported-file-extensions` | Map of supported file extensions and their types                      |
| `feign.client.config.digiwf-process-config.url`        | Url of the process service                                            |

To use the streaming adapter, you need to set the properties as described in
the [DigiWF Message library](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/README.md#configuration).

The authorization is outsourced to the digiwf-spring-security-starter. You also need to set the properties as described
in
the [DigiWF Spring Security library](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-spring-security/README.md).

## Run the Digiwf DMS Integration Service

To run the service i.e. in Open Shift you can set the environment variables below or run the service locally as
described in the [Getting Started](#getting-started) section.

### Configuration with environment variables

Set following environment variables to configure the service. Those are abbreviations of the properties above to shorten
the configuration in an environment like docker.

| Environment Variable               | Description                                                                                     |
|------------------------------------|-------------------------------------------------------------------------------------------------|
| `MUCS_DMS_INTEGRATION_SERVER_PORT` | Port of the MUCS DMS Application                                                                |
| `ALW_DMS_INTEGRATION_SERVER_PORT`  | Port of the ALW DMS Application                                                                 |
| `DIGIWF_ENV`                       | Environment in which the services runs                                                          |
| `FABASOFT_DMS_USERNAME`            | technical fabasoft dms user                                                                     |
| `FABASOFT_DMS_PASSWORD`            | technical fabasoft dms password                                                                 |
| `FABASOFT_DMS_HOST`                | fabasoft url                                                                                    |
| `FABASOFT_DMS_PORT`                | fabasoft port                                                                                   |
| `FABASOFT_ENABLE_MTOM`             | Enables MTOM default is true. Should be disabled with mocking                                   |
| `S3_MAX_FILE_SIZE`                 | Maximum allowed file size that can be downloaded from S3                                        |
| `S3_MAX_BATCH_SIZE`                | Maximum allowed total size of files that can be processed at one time                           |
| `KAFKA_SECURITY_PROTOCOL`          | Security protocol of kafka (default is PLAINTEXT)                                               |
| `KAFKA_BOOTSTRAP_SERVER`           | kafka server address (default is localhost)                                                     |
| `KAFKA_BOOTSTRAP_SERVER_PORT`      | kafka server port (default is 29092)                                                            |
| `SSO_ISSUER_URL`                   | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}` |
| `SSO_BASE_URL`                     | Base url used for sso connection.                                                               |
| `SSO_REALM`                        | Realm used for sso connection.                                                                  |
| `DIGIWF_SECURITY_CLIENT-ID`        | SSO client id used for sso connection.                                                          |
| `DIGIWF_SECURITY_CLIENT-SECRET`    | SSO secret id used for sso connection.                                                          |

### Getting Started

1. Build it with `mvn clean install`
2. Run the Stack using `docker-compose`
3. Run the Dms Integration ([digiwf-dms-integration-service](digiwf-dms-integration-service)

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
    5. DigiwfDmsApplication
        - Activate Spring profile `local`
        - Add Environment values from `stack/local-docker.env`
2. Test the functionality with the
   process [example-dms-V01](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/dms-example-V01)

