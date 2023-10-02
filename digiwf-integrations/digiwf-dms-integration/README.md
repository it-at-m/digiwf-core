# Digiwf DMS Integration

The following steps are needed to run the integration locally.

## Getting Started

1. Build it with `mvn clean install`
2. Run the Stack using `docker-compose`
3. Run the Dms Integration ([digiwf-dms-integration-service](digiwf-dms-integration-service)

## Configuration

| Environment Variable        | Description                                                   |
|-----------------------------|---------------------------------------------------------------|
| DMS_INTEGRATION_SERVER_PORT | Port of the Application                                       |
| DIGIWF_ENV                  | Environment in which the services runs                        |
| KAFKA_SECURITY_PROTOCOL     | Security protocol of kafka (default is PLAINTEXT)             |
| KAFKA_BOOTSTRAP_SERVER      | kafka server address (default is localhost)                   |
| KAFKA_BOOTSTRAP_SERVER_PORT | kafka server port (default is 29092)                          |
| FABASOFT_DMS_USERNAME       | technical fabasoft dms user                                   |
| FABASOFT_DMS_PASSWORD       | technical fabasoft dms password                               |
| FABASOFT_DMS_HOST           | fabasoft url                                                  |
| FABASOFT_DMS_PORT           | fabasoft port                                                 |
| FABASOFT_ENABLE_MTOM        | Enables MTOM default is true. Should be disabled with mocking |

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
    5. DigiwfDmsApplication
        - Activate Spring profile `local`
        - Add Environment values from `stack/local-docker.env`
2. Test the functionality with the
   process [example-dms-V01](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/dms-example-V01)

