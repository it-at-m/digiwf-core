## Getting started

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