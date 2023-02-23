# Stack

For local development you can use the following tools:

- **Event Bus**: Kafka (and Zookeeper)
- **S3 Storage**: [Minio](https://min.io/docs/minio/linux/index.html)
- **Email Server**: [Mailhog](https://github.com/mailhog/MailHog)

## Docker

Use docker compose to start the infrastructure components:

```bash
docker compose up -d

# with frontend
docker compose --profile tasklist up -d
```

Additionally, start the backend (digiwf-engine-service) with the profiles `local, no-security, streaming, no-ldap`.

> An example process on how to use the miranum-ide is available [here](https://github.com/FlowSquad/miranum-ide/tree/main/resources/example-process).

### Running the stack for the first time

**Setup Minio**

1. Go to [http://localhost:9001/](http://localhost:9001/)
2. Sign in with *minio* and *Test1234*
3. Create a bucket
4. Create a service account
5. Add the bucket name and service account secrets as env variables to the digiwf-s3-integration
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_BUCKETNAME`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_ACCESSKEY`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_SECRETKEY`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_URL=http://localhost:9000`


## LHM PC

- [Instructions for WSL](https://git.muenchen.de/ext.dl.moesle/digiwf-local-setup)

## Additional Properties/Envs

Set the following properties either in an `.env` file or add them in a `custom application-*.properties`.

```
IO_MUENCHENDIGITAL_DIGIWF_S3_BUCKETNAME=
IO_MUENCHENDIGITAL_DIGIWF_S3_ACCESSKEY=
IO_MUENCHENDIGITAL_DIGIWF_S3_URL=http://localhost:9000
IO_MUENCHENDIGITAL_DIGIWF_S3_SECRETKEY=

IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENREQUESTURL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_URL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTID=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTSECRET=

KAFKA_BOOTSTRAP_SERVER=localhost
KAFKA_BOOTSTRAP_SERVER_PORT=29092

MAIL_HOST=localhost
MAIL_PORT=1025
MAIL_USERNAME=digiwf@muenchen.de
MAIL_PASSWORD=secret

SSO_BASE_URL=http://keycloak:8080/auth
SSO_REALM=P82
SSO_ISSUER_URL=${SSO_BASE_URL}/realms/${SSO_REALM}
SSO_ENGINE_CLIENT_ID=digitalwfv2
SSO_ENGINE_CLIENT_SECRET=

DIRECTORY_LDAP_CONTEXTSOURCE=
DIRECTORY_LDAP_PERSONSEARCHBASE=
DIRECTORY_LDAP_PERSONOBJECTCLASSES=
DIRECTORY_LDAP_OUSEARCHBASE=
DIRECTORY_LDAP_OUOBJECTCLASSES=
```
