# DigiWF OpenAI Integration

## Getting started

1. Run the *stack* from the root directory with `docker-compose -f stack/local.yml up`.
2. Build the project with `mvn clean install`
3. Run the openai integration with:
  * the profile `local` and optionally `azure` if you`re using [Microsoft Azure](https://azure.microsoft.com/) as OpenAI provider 
  * the environment variables from [../stack/local.env](../stack/local.env) and the OpenAI-API configurations set.

## Configuration

| Environment Variable                       | Description                                      | Default |
|--------------------------------------------|--------------------------------------------------|---------|
| `DIGIWF_ENV`                               | Environment in which the services runs           |         |
| `OPENAI_INTEGRATION_BASE-URL`              | Base-URL of OpenAI API (only non Azure)          |         |
| `OPENAI_INTEGRATION_API-KEY`               | API-Key for OpenAI API                           |         |
| `OPENAI_INTEGRATION_MODEL`                 | Default Large Language Model for Requests        |         |
| `OPENAI_INTEGRATION_LOGGING`               | Verbose Request Logging                          | `false` |
| `OPENAI_INTEGRATION_TEMPERATURE`           | Default Temperature to use for AI Requests       | `0.7`   |
| `OPENAI_INTEGRATION_MAX-TOKENS`            | Max Tokens to use for AI Requests                | `1000`  |
| `OPENAI_INTEGRATION_AZURE_API-VERSION`     | API-Version of Azure Deployment (only Azure)     |         |
| `OPENAI_INTEGRATION_AZURE_DEPLOYMENT-NAME` | Deployment name of Azure deployment (only Azure) |         |
| `OPENAI_INTEGRATION_AZURE_RESOURCE`        | Resource name of Azure deployment (only Azure)   |         |
