# Digiwf Address Integration 

The following steps are needed to run the integration locally.

## Getting started

1. Build it with `mvn clean install`
2. Execute the e2e test [AddressIntegrationE2eTest.java](digiwf-address-integration-service/src/test/java/de/muenchen/oss/digiwf/address/integration/AddressIntegrationE2eTest.java)

## Testing functionality

The [AddressIntegrationE2eTest.java](digiwf-address-integration-service/src/test/java/de/muenchen/oss/digiwf/address/integration/AddressIntegrationE2eTest.java) is a test of the integrations functionality using an embedded kafka instance and wiremock to mock the api to the Address-Service.
