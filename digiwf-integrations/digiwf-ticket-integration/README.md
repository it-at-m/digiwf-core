# DigiWF Ticket Integration

## Getting started

1. Run the *stack* from the root directory with `docker-compose -f stack/local.yml up`.
2. Build the project with `mvn clean install`
3. Run the ticket integration with the profile `local` and the environment variables from [../stack/local.env](../stack/local.env) set.

## Configuration

- **DIGIWF_ENV** - The environment of DigiWF, e.g. `local-01`, `dev`, `test`, `demo`.
- **SSO_TICKET_CLIENT_ID** - The client id of the ticket service.
- **SSO_TICKET_CLIENT_SECRET** - The client secret of the ticket service.
- **TICKETING_ZAMMAD_URL** - The URL of the ticket service (e.g. zammad).
- **ENGINE_REST_ENDPOINT_URL** - The URL of the engine server.
- **DOCUMENT_STORAGE_HOST** - The host of the document storage service.
- **DOCUMENT_STORAGE_PORT** - The port of the document storage service.
- **SSO_ISSUER_URL** - The URL of the SSO issuer.
- **SSO_BASE_URL** - The base URL of the SSO service.
- **SSO_REALM** - The realm of the SSO service.
- **ZAMMAD_SSO_ISSUER_URL** - The URL of the SSO issuer for the ticket service.
- **ZAMMAD_SSO_BASE_URL** - The base URL of the SSO service for the ticket service.
- **ZAMMAD_SSO_REALM** - The realm of the SSO service for the ticket service.
- **SSO_S3_CLIENT_ID** - The client id of the S3 service.
- **SSO_S3_CLIENT_SECRET** - The client secret of the S3 service.
- **TICKETING_INTEGRATION_SERVER_PORT** - The port of the ticket integration server.

> Note: The Ticket Integration uses 2 different sso clients from two different realms (s3 and zammad). 

**Configure the supported file extensions**

```yaml
de:
  muenchen:
    oss:
      digiwf:
        ticketing:
          supportedFileExtensions:
            - "application/pdf"
            - "text/plain"
            - "image/png"
            - "image/jpeg"
            - "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            - "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            - "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            - "application/vnd.visio2013"
            - "application/vnd.ms-project"
            - "application/vnd.oasis.opendocument.formula"
            - "application/vnd.oasis.opendocument.presentation"
            - "application/vnd.oasis.opendocument.spreadsheet"
            - "application/vnd.oasis.opendocument.text"
```
