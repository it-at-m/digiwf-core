# Digiwf Email Integration

The following steps are needed to run the integration locally.

## Getting Started

1. Build it with `mvn clean install`
2. Run the Stack using `docker-compose`
3. Run the Email Integration ([digiwf-email-integration-service](digiwf-email-integration-service) or [digiwf-email-integration-example](digiwf-email-integration-example))

## Testing functionality

1. Start the DigiwfEmailExampleApplication
2. Run the [example http requests](digiwf-email-integration-example/src/main/resources/rest-api-client/example.http) in IntelliJ
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
2. Test the functionality with the process [example-email-V02](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/email-example-V02)
