# Digiwf Cosys Integration

Checkout the documentation at tbd.

## Getting started

```
# build
# execute in the root directory
mvn clean install
```

Execute the [Example](digiwf-cosys-integration-example) application and try the integrations features out.

To use the CoSys integration you have to set the following configurations.

### security

- spring.security.oauth2.client.registration.cosys.authorization-grant-type=client_credentials
- spring.security.oauth2.client.registration.cosys.client-id=xx
- spring.security.oauth2.client.registration.cosys.client-secret=xxx
- spring.security.oauth2.client.provider.cosys.token-uri=xxx
-

### cosys

- io.muenchendigital.digiwf.cosys.url=xxx
- io.muenchendigital.digiwf.cosys.merge.datafile=//multi
- io.muenchendigital.digiwf.cosys.merge.inputLanguage=Deutsch
- io.muenchendigital.digiwf.cosys.merge.outputLanguage=Deutsch
- io.muenchendigital.digiwf.cosys.merge.keepFields=unresolved-ref

## Local setup

To have a full local setup of DigiWF to test the integration with BPMN processes you need to start the following services:
* zookeeper/kafka
* digiwf-engine-service
* digiwf-gateway
* digiwf-tasklist
* digiwf-s3-integration-service
* digiwf-camunda-connector-service

and the digiwf-cosys-integration-service itself of course.

And don't forget to activate the _streaming_ profile where possible.

## Testing with DigiWF

To let you try out the integration artifact with the DigiWF Tasklist application we provided some test process resources. You'll find them in digiwf-cosys-integration-service/src/test/resources/process.

Just deploy these into your engine together with the StreamingTemplateV02 from digiwf-connector-service.
Boot your local environment and start the process "Cosys GenerateDocument Test (Streaming)".

