# DigiWF Example Integration

The digiwf-example-integration is a very simple example integration that demonstrates how to implement a new integration using the [**digiwf-integration-lib**](../../digiwf-libs/digiwf-integration-lib).

In the **core** module the integrations business logic is implemented.
In the **starter** module the Spring Beans are implemented.
The **example application** demonstrates the usage of the starter.
The **service application** is preconfigured and can be used directly. This service application is usually published as a Docker image on DockerHub.

## Getting Started

```
# build
# execute in the root directory
mvn clean verify
```

Execute the [Example](digiwf-example-integration-example) application, go to [http://localhost:8080](http://localhost:8080/) and check the logs to see that the integration is executed.

