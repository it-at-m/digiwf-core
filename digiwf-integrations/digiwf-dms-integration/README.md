# Digiwf DMS Integration

The following steps are needed to run the integration locally.

## Getting Started

1. Build it with `mvn clean install`
2. Run the Stack using `docker-compose`
3. Run the Dms Integration ([digiwf-dms-integration-service](digiwf-dms-integration-service)

## Testing functionality

1. Start the DigiwfDmsApplication

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
