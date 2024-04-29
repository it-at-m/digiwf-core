# digiwf-engine-rest-service

Service which provides camunda rest api by directly accessing the engine db.
Is mainly intended for usage with optimize to relieve the [digiwf-engine-service](../digiwf-engine-service).

## Profiles

Following Spring-Profiles are available and provide described functionality. There may be additional profiles provided
by dependencies.

| Name          | Description                                                                        |
|---------------|------------------------------------------------------------------------------------|
| `groups-ldap` | Intersect camunda user to group resolution and resolve via LDAP.                   |
| `groups-mock` | Intersect camunda user to group resolution and always return `digiwf-webapp-user`. |

## Configuration

Following the environment variables to configure the service. There may be additional aliases for some of the mentioned
properties.

| Env-Var                                                     | Description                                                               |
|-------------------------------------------------------------|---------------------------------------------------------------------------|
| `SPRING_DATASOURCE_URL` / `ENGINE_DATASOURCE_URL`           | JDBC url to the engine database.                                          |
| `SPRING_DATASOURCE_USERNAME` / `ENGINE_DATASOURCE_USER`     | Username for the engine db.                                               |
| `SPRING_DATASOURCE_PASSWORD` / `ENGINE_DATASOURCE_PASSWORD` | Password for the engine db.                                               |
| `LDAP_URL`                                                  | Url to ldap endpoint used for group resolution via `groups-ldap` profile. |
| `LDAP_USERNAME`                                             | Username used for ldap connection.                                        |
| `LDAP_PASSWORD`                                             | Password used for ldap connection.                                        |
| `LDAP_GROUP_BASE`                                           | Base dn used to search for groups.                                        |
| `LDAP_USER_BASE`                                            | Base dn used to search for users.                                         |

### [digiwf-spring-security](../../digiwf-libs/digiwf-spring-security)

| Env-Var                                                      | Description                                                                                     |
|--------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| `SSO_ISSUER_URL`                                             | Issuer url used for authenticating incoming requests i.e. `${SSO_BASE_URL}/realms/${SSO_REALM}` |
| `SSO_BASE_URL`                                               | Base url used for sso connection.                                                               |
| `SSO_REALM`                                                  | Realm used for sso connection.                                                                  |
| `DIGIWF_SECURITY_CLIENT-ID` / `SSO_ENGINE_CLIENT_ID`         | SSO client id used for sso connection.                                                          |
| `DIGIWF_SECURITY_CLIENT-SECRET` / `SSO_ENGINE_CLIENT_SECRET` | SSO secret id used for sso connection.                                                          |
