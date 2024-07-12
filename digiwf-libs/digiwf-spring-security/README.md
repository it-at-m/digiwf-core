# Digiwf Spring Security

To ensure access to DigiWF components and connected services, the environment
variables `SSO_ISSUER_URL`, `SSO_BASE_URL`, `SSO_REALM`, `DIGIWF_SECURITY_CLIENT-ID` and `DIGIWF_SECURITY_CLIENT-SECRET`
or the following properties must be set:

```
digiwf.security.client-id
digiwf.security.client-secret
spring.security.oauth2.resourceserver.jwt.issuer-uri
spring.security.oauth2.client.provider.keycloak.issuer-uri
spring.security.oauth2.client.provider.keycloak.user-info-uri
spring.security.oauth2.client.provider.keycloak.jwk-set-uri
```