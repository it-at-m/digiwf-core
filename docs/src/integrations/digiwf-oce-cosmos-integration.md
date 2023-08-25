# DigiWF Oce-Cosmos Integration

## Documentation

Basically, two artifacts are available.
This is on the one hand the Spring-Boot-Starter `digiwf-oce-cosmos-integration-starter`
and on the other hand the service `digiwf-oce-cosmos-integration-service` which is provided as an
[Image](https://hub.docker.com/repository/docker/itatm/digiwf-oce-cosmos-integration-service).

### Spring-Boot-Starter

The usage of the starter is documented
in [quickstart.md](https://github.com/it-at-m/digiwf-oce-cosmos-integration#getting-started).

#### Error handling

The errors occurring during the rest request are divided into three error categories.
These are client-side errors, server-side errors and errors that cannot be assigned to either the client or the server.
Each of these three error categories is assigned its own exception....

tbd

### Service provided as an image

The service is provided via Dockerhub as
an [Image](https://hub.docker.com/repository/docker/itatm/digiwf-oce-cosmos-integration-service).
The source code for the service can be found in
submodule [digiwf-oce-cosmos-integration-service](https://github.com/it-at-m/digiwf-oce-cosmos-integration/tree/dev/digiwf-oce-cosmos-integration-service)
.

The requests to the oce cosmos are expected by the service over kafka event bus messages.
To provide the event bus functionality via kafka,
the [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils) are used.

#### Service Configuration

An example configuration can be found within the properties files.

* `application.yml`: `digiwf-oce-cosmos-integration-service/src/main/resources/application.yml`
* `application-local.yml`: `digiwf-oce-cosmos-integration-service/src/main/resources/application-local.yml`

The file `application.yml` contains the configuration according graceful shutdown, metrics, ports, ...

The file `application-local.yml` provides the event bus and oce cosmos relevant configuration.
The event bus configuration is implemented
according [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started).

#### Service API usage

tbd

#### Error handling

If client-side errors, server-side errors or errors that cannot be assigned to either the client or the server occur
during the rest request within the service, an error response is returned to the caller via the event bus.

```json
{
  "message": "THE GENERIC ERROR MESSAGE"
}
```
