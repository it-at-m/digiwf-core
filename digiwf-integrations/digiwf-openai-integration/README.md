# Digiwf OpenAI Integration 

The following steps are needed to run the integration locally.

## Getting started

1. Build it with `mvn clean install`
2. Execute the e2e test [openaiIntegrationE2eTest.java](digiwf-openai-integration-service/src/test/java/de/muenchen/oss/digiwf/openai/integration/openaiIntegrationE2eTest.java)

## Testing functionality

The [openaiIntegrationE2eTest.java](digiwf-openai-integration-service/src/test/java/de/muenchen/oss/digiwf/openai/integration/openaiIntegrationE2eTest.java) is a test of the integrations functionality using an embedded kafka instance and wiremock to mock the api to the openai.
