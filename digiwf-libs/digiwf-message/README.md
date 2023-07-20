# DigiWF Message

The **DigiWF Message** library is an abstraction layer that simplifies communication between different components and reduces technical complexity.

The idea behind the **DigiWF Message** library is to solve the recurring Spring Cloud Stream configurations in a central location and provide an API to use them.
This way, an integration developer no longer needs to delve into the depths of Spring Cloud Stream configurations and can focus on implementing the integration.

The library provides its own APIs for sending messages that send messages to a message broker.
In addition, the library also provides a Spring Cloud Stream `RoutingCallback` for consuming and forwarding messages (function routing).


## Usage

The **DigiWF Message** library provides the MessageApi, which is used to send messages.

In addition, APIs are provided for recurring messages that build on the MessageApi.
For this purpose, we have created the ProcessApi and the ErrorApi.
The ProcessApi can be used to start processes in DigiWF, correlate messages to processes, and perform error handling.
The ErrorApi provides the exceptions `BpmnError` for business errors and `IncidentError` for technical errors, which can be thrown and caught in the application.
In addition, methods are provided, as with the ProcessApi, to send messages to the corresponding destinations (target topics).

The destinations for the different actions can be configured via `application.yml` (see [Configuration](#configuration)).

> Usage examples can be found in the [Example Module](digiwf-message-example).

### MessageApi

The MessageApi provides the `sendMessage` method, which can be used to send a message to a specific *destination*.
A message consists of a `payload` and `headers`.
The payload contains the data to be transmitted. The headers are a key, value pair that contains additional information about the message.

> At DigiWF, we use Spring Cloud Stream to send messages to Kafka (message broker).
> The payload is the event that is sent to Kafka. The headers contain important information such as the process instance id, the type of event, etc.

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


## Spring Cloud Stream Components

The DigiWF Message library provides Spring Cloud Stream components that can be used to send and receive messages to Kafka.
For this purpose, event emitters (`Sinks`) and a function router (`RoutingCallback`) are provided.

### Event Emitter

An event emitter `sendMessage` is provided, which is used to send messages to the corresponding destinations.
Internally, the MessageApi uses the `spring.cloud.stream.sendto.destination` header, which automatically sends outgoing messages to the topic specified as the destination.

However, in order for Spring Cloud Stream to send messages, an outgoing channel must be configured.
For this purpose, it is recommended to set the `spring.cloud.stream.bindings.sendMessage-out-0.destination` property and define the function `sendMessage` under `spring.cloud.function.definition`.

### Function Router

In addition to sending messages via the event emitter, messages can also be received via the function router.
For this purpose, we provide a `RoutingCallback` that routes incoming messages to the application's consumers based on the `type` header.
The mapping between the `type` header, which is read from the incoming messages, and the consumer functions is configured in the `application.yml` via the `io.muenchendigital.digiwf.message.typeMappings` property.


## Konfiguration

```yaml
spring:
  cloud:
    stream:
      function:
        routing:
          enabled: 'true'
      bindings:
        functionRouter-in-0:
          group: "dwf-digiwf-example-integration-local-01"
          destination: "digiwf-example-integration-local-01"
        sendMessage-out-0:
          destination: "digiwf-example-integration-local-01"
    function:
      definition: functionRouter;sendMessage;
[...]
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "digiwf-example-integration-incident"
        technicalErrorDestination: "digiwf-example-integration-technical-error"
        correlateMessageDestination: "digiwf-example-integration-correlate-message"
        startProcessDestination: "digiwf-message-scs-example-start-process"
        typeMappings:
          messageType: "consumer"
```

|                                                               |                                                                                   |
|---------------------------------------------------------------|-----------------------------------------------------------------------------------|
| io.muenchendigital.digiwf.message.incidentDestination         | Destination to redirect incidents to (e.g. Kafka Topic)                           |
| io.muenchendigital.digiwf.message.technicalErrorDestination   | Destination to redirect technical errors a.k.a. bpmn errors to (e.g. Kafka Topic) |
| io.muenchendigital.digiwf.message.correlateMessageDestination | Destination to send correlate messages to (e.g. Kafka Topic)                      |
| io.muenchendigital.digiwf.message.startProcessDestination     | Destination to send start process messages to (e.g. Kafka Topic)                  |
| io.muenchendigital.digiwf.message.typeMappings                | Mapping of message types to consumer function names                               |



## Customizability

For the ErrorApi, ProcessApi, and MessageApi, we provide a default implementation based on Spring Cloud Stream.
If you want to change this implementation, you can implement the corresponding interfaces and provide them as beans.

An example of a MessageApi implementation that only logs messages can be found in our Example.
