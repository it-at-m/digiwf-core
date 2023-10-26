# Stack

For local development you can use the following tools:

- **SSO**: [Keycloak](https://www.keycloak.org/)
- **Event Bus**: Kafka (and Zookeeper)
- **S3 Storage**: [Minio](https://min.io/docs/minio/linux/index.html)
- **Email Server**: [Mailhog](https://github.com/mailhog/MailHog)
- **DB**: Postgresql databases 

## Docker

Use docker compose to start the infrastructure components:

```bash
docker compose up -d

# with frontend
docker compose --profile tasklist-frontend up -d
```

> Also checkout our documentation [https://digiwf.oss.muenchen.de/documentation/guides/technical-setup/#lokale-infrastruktur](https://digiwf.oss.muenchen.de/documentation/guides/technical-setup/#lokale-infrastruktur).

## Running Process Engine Backend

To start the backend (`digiwf-engine-service`), run `de.muenchen.oss.digiwf.EngineServiceApplication` with the
profiles `local, streaming, no-ldap`. The application requires environment variables to be set. For this purpose,
the `stack/local-docker.env` should be used. If you run IDEA IntelliJ,
the [.EnvFile plugin](https://plugins.jetbrains.com/plugin/7861-envfile) might be helpful to
add the env file to your run configuration.

### Running the stack for the first time

## Additional Properties/Envs

There is a set of properties defined in `local-docker.env`.
Set the following properties either in an `.env` file or add them in a `custom application-*.properties`.

```
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENREQUESTURL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_URL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTID=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTSECRET=

DIRECTORY_LDAP_CONTEXTSOURCE=
DIRECTORY_LDAP_PERSONSEARCHBASE=
DIRECTORY_LDAP_PERSONOBJECTCLASSES=
DIRECTORY_LDAP_OUSEARCHBASE=
DIRECTORY_LDAP_OUOBJECTCLASSES=
```

## LHM PC

- [Instructions for WSL](https://git.muenchen.de/ext.dl.moesle/digiwf-local-setup)
