# DigiWF Message Example

> Example application to demonstrate the usage of the DigiWF Message library.
> For more information about the DigiWF Message library, please refer to the [dokumentation](https://digiwf.oss.muenchen.de//documentation/libs/digiwf-message/).

The DigiWF Message library demonstrates the usage with and without sending message to a kafka broker.
Internally it uses spring cloud stream to fulfill this task.
If you want to use this example with a kafka broker, you have to start the application with the profile `streaming` and
configure the kafka broker with the environment variables:

- `KAFKA_BOOTSTRAP_SERVER` e.g. `localhost`
- `KAFKA_BOOTSTRAP_SERVER_PORT` e.g. `29092`
- `KAFKA_SECURITY_PROTOCOL` e.g. `PLAINTEXT`

## Application Structure

The application is structured in the following way:

- `adapter` contains a custom implementation of the `MessageApi` that logs all messages to the console. It is only enabled if the profile `streaming` is not active.
- `message` contains an example domain to demonstrate the usage of the `MessageApi`.
- `process` contains an example domain to demonstrate the functions of the `ProcessApi` and the `ErrorApi`.
- `streaming` contains a spring cloud stream consumer that logs messages to the command line. It is only enabled if the profile `streaming` is active.

## Usage

Build and start the example application. It provides REST APIs to trigger the different functions of the DigiWF Message library.
You can use the example requests from [`src/main/resources`](src/main/resources) to test the application.
