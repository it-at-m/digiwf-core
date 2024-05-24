# digiwf-optimize-plugins

Collection
of [Optimize-Plugins](https://docs.camunda.io/optimize/self-managed/optimize-deployment/plugins/plugin-system/)

## Plugins

Following plugins are contained:

- OAuth2AuthenticationExtractor
- OAuth2EngineRestFilter
- FilterSensitiveVariableImportAdapter

## Usage/Deployment

1. Package project to jar with `mvn package`
2. Append jar to Optimize
    1. By building custom image with base optimize and add jar (see [Dockerfile](./Dockerfile))
    2. By attaching jar to container (i.e. via kubernetes/openshift ConfigMap and VolumeMount)
3. Provide environment variables required by plugins (i.e. via ConfigMap)
4. Configure Optimize to use plugins

## Configuration

The following environment variables are required by some plugins and need to be provided.

| Evn-Var                                        | Description                                                                                                    |
|------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `SSO_ISSUER_URL`                               | Issuer url to request client access token from and verify incoming access token against.                       |
| `SSO_SCOPE`                                    | Scope for oAuth2. Default `openid profile`.                                                                    |
| `SSO_OPTIMIZE_CLIENT_ID`                       | Client id to use for request to engine-rest.                                                                   |
| `SSO_OPTIMIZE_CLIENT_SECRET`                   | Client secret for `SSO_OPTIMIZE_CLIENT_SECRET`.                                                                |
| `GLOBAL_VAR_WHITELIST`                         | Comma seperated list of variable names which are imported into optimize.                                       |
| `PROCESS_VAR_WHITELIST_<processDefinitionKey>` | Comma seperated list of variable names which are imported into optimize for a specific process definition key. |

## Development

The build of this package needs to be executed with maven profile `camunda-ee` and without `camunda-ce`.

1. Start [stack](../../stack) with profile `optimize`
    - Starts dependencies: keycloak, elasticsearch and optimize
    - Optimize container needs to be recreated to apply plugin changes
2. Start [engine-rest-service](../../digiwf-engine/digiwf-engine-rest-service) and [gateway](../../digiwf-gateway)
3. Goto http://<gateway-url>/optimize i.e. http://localhost:8083/optimize
