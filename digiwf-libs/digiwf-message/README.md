# DigiWF Message

The **DigiWF Message** library is an abstraction layer that simplifies communication between different components and
reduces technical complexity.

The idea behind the **DigiWF Message** library is to solve the recurring Spring Cloud Stream configurations in a central
location and provide an API to use them.
This way, an integration developer no longer needs to dive into the depths of Spring Cloud Stream configurations and
can focus on implementing the integration.

The library provides its own APIs for sending messages to a message broker.
Additionally, the library also configures event routing, where messages can be assigned to a `Consumer` simply by
matching the name of the header type and the consumer.

## Usage

The **DigiWF Message** library provides the MessageApi, which is used to send messages.

In addition, APIs are provided for recurring message types that build on the MessageApi.
For this purpose, we have created the ProcessApi and the ErrorApi.
The ProcessApi can be used to start processes in DigiWF, correlate messages to processes, and perform error handling.
The ErrorApi provides the exceptions `BpmnError` for business errors and `IncidentError` for technical errors, which can
be thrown and caught in the application.

The destinations for the different actions can be configured via `application.yml` (
see [Configuration](#configuration)).

> Usage examples can be found in the [Example Module](digiwf-message-example).

### MessageApi

The MessageApi provides the `sendMessage` method, which can be used to send a message to a specific *destination*.
A message consists of a `payload` and `headers`.
The payload contains the data to be transmitted. The headers are a key, value pair that contains additional information
about the message.

> At DigiWF, we use Spring Cloud Stream to send messages to Kafka (message broker).
> The payload is the event that is sent to Kafka. The headers contain important information such as the process instance
> id, the type of event, etc.

**Usage Example**

```java

@RequiredArgsConstructor
public class MessageServiceExample {
    private final MessageApi sendMessageApi;

    public void sendMessageExample(final Message message) {
        // send a message to the destination
        final boolean success = this.sendMessageApi.sendMessage(message, "my-destination");
        System.out.println("Message sent: " + success);
    }

    public void sendMessageWithHeadersExample(final Message message) {
        // example with headers
        final Map<String, Object> headers = Map.of("key", "value");
        final boolean success = this.sendMessageApi.sendMessage(message, headers, "my-destination");
        System.out.println("Message sent: " + success);
    }
}
```

### ProcessApi

The `ProcessApi` interface provides methods for starting processes and correlating messages in processes.
In the background, the ProcessAPI uses the MessageApi to send messages to the appropriate destinations.
The destinations for different actions can be configured via the `application.yml`.

**Usage Example**

```java

@RequiredArgsConstructor
public class ProcessService {
    private final ProcessApi processApi;

    public void sendMessages() {
        // Start a new process with key "myProcess" and some variables
        processApi.startProcess("myProcess", new HashMap<String, Object>());

        // Start a new process with key "myProcess", some variables, and a fileContext
        processApi.startProcess("myProcess", new HashMap<String, Object>(), "fileContext");

        // Correlate a message with the process instance ID and some variables
        processApi.correlateMessage("123", "myMessage", new HashMap<String, Object>());
    }
}
```

## Spring Cloud Stream Event Routing

The DigiWF Message Library also configures the properties for event routing of Spring Cloud Stream. With event routing,
messages can be assigned to a Consumer if the name of the consumer and the header `type` match. (See
[https://docs.spring.io/spring-cloud-stream/reference/spring-cloud-stream/event-routing.html](https://docs.spring.io/spring-cloud-stream/reference/spring-cloud-stream/event-routing.html).)
Users of the library only need to set the following properties.

## Configuration

```yaml
spring:
  cloud:
    stream:
      bindings:
        functionRouter-in-0:
          group: "dwf-digiwf-example-integration-local-01"
          destination: "digiwf-example-integration-local-01"
        sendMessage-out-0:
          destination: "digiwf-example-integration-local-01"
  [ ... ]
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "digiwf-example-integration-incident"
        bpmnErrorDestination: "digiwf-example-integration-technical-error"
        correlateMessageDestination: "digiwf-example-integration-correlate-message"
        startProcessDestination: "digiwf-message-scs-example-start-process"
        deadLetterQueueDestination: "digiwf-example-integration-incident"
```

| Property                                                        | Description                                                                                              |
|-----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| `spring.cloud.stream.bindings.functionRouter-in-0.group`        | Group name for the consumer(s) bound to the functionRouter-in-0 input binding                            |
| `spring.cloud.stream.bindings.functionRouter-in-0.destination`  | Destination (or kafak topic) to which the `functionRouter-in-0` input binding should listen for messages |
| `spring.cloud.stream.bindings.sendMessage-out-0.destination`    | Destination to which the `sendMessage-out-0` output binding should send messages                         |
| `io.muenchendigital.digiwf.message.incidentDestination`         | Destination to redirect incidents to (e.g. Kafka Topic)                                                  |
| `io.muenchendigital.digiwf.message.bpmnErrorDestination`        | Destination to redirect technical errors a.k.a. bpmn errors to (e.g. Kafka Topic)                        |
| `io.muenchendigital.digiwf.message.correlateMessageDestination` | Destination to send correlate messages to (e.g. Kafka Topic)                                             |
| `io.muenchendigital.digiwf.message.startProcessDestination`     | Destination to send start process messages to (e.g. Kafka Topic)                                         |
| `io.muenchendigital.digiwf.message.deadLetterQueueDestination`  | Destination to send failing messages events to (e.g. Kafka Topic)                                        |

## Customizability

For the ErrorApi, ProcessApi, and MessageApi, we provide a default implementation based on Spring Cloud Stream.
If you want to change this implementation, you can implement the corresponding interfaces and provide them as `@Beans`.

An example of a MessageApi implementation that only logs messages can be found in
our [Example](https://github.com/it-at-m/digiwf-core/blob/dev/digiwf-libs/digiwf-message/digiwf-message-example/src/main/java/de/muenchen/oss/digiwf/message/example/adapter/NoStreamingMessageAdapter.java).
