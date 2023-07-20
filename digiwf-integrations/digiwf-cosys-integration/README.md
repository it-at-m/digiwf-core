# Digiwf Cosys Integration

The following steps are needed to run the integration locally.

## Getting started

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

## Testing functionality

1. Start DigiwfCosysExampleApplication
   - Activate Spring profile `local,streaming`
   - Add Environment values from `stack/local-docker.env`
     
2. Execute the [Example](digiwf-cosys-integration-example) application and try the integrations features out.


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
   5. DigiwfCosysApplication
      - Activate Spring profile `local,streaming`
      - Add Environment values from `stack/local-docker.env`
2. Test the functionality with process [Example Cosys GenerateDocument (Streaming)](../../digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/cosys-integration)

