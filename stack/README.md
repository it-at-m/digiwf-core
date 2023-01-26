# Stack

For local development you can use the following tools:

- **Event Bus**: Kafka (and zookeeper)
- **S3 Storage**: [Minio](https://min.io/docs/minio/linux/index.html)
- **Email Server**: [Mailhog](https://github.com/mailhog/MailHog)

## Docker

Use docker compose to start the infrastructure components:

```bash
docker compose up -d

# with frontend
docker compose --profile tasklist up -d
```

Additionally, start the backend (digiwf-engine-service) with the profiles `local, no-security, no-mail, streaming, no-ldap`.

> An example process on how to use the miranum-ide is available [here](https://github.com/FlowSquad/miranum-ide/tree/main/resources/example-process).

## LHM PC

- [Instructions for WSL](https://git.muenchen.de/ext.dl.moesle/digiwf-local-setup)

## Additional Properties/Envs

Set the following properties either in an `.env` file or add them in a `custom application-*.properties`.

```
SPRING_CLOUD_STREAM_KAFKA_BINDER_BROKERS=localhost:29092
IO_MUENCHENDIGITAL_DIGIWF_S3_BUCKETNAME=
IO_MUENCHENDIGITAL_DIGIWF_S3_ACCESSKEY=
IO_MUENCHENDIGITAL_DIGIWF_S3_URL=http://localhost:9000
IO_MUENCHENDIGITAL_DIGIWF_S3_SECRETKEY=
SECURITY_OAUTH2_RESOURCE_JWK_KEY-SET-URI=
SECURITY_OAUTH2_RESOURCE_USER-INFO-URI=
SPRING_MAIL_USERNAME=
SPRING_MAIL_HOST=
SPRING_MAIL_PORT=
KEYCLOAK_AUTH-SERVER-URL=
DIRECTORY_LDAP_CONTEXTSOURCE=
DIRECTORY_LDAP_PERSONSEARCHBASE=
DIRECTORY_LDAP_PERSONOBJECTCLASSES=
DIRECTORY_LDAP_OUSEARCHBASE=
DIRECTORY_LDAP_OUOBJECTCLASSES=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENREQUESTURL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_URL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTID=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTSECRET=
```
