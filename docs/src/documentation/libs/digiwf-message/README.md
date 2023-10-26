# DigiWF Message

[![](https://img.shields.io/badge/Example-digiwf_message_example-informational?style=flat&logo=Github&logoColor=white&color=D4B300)](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-message/digiwf-message-example/)

Die **DigiWF Message** Bibliothek ist eine Abstraktionsschicht, um die Kommunikation zwischen den verschiedenen Komponenten
zu vereinfachen und technische Komplexität zu reduzieren.

Die Idee hinter der **DigiWF Message** Bibliothek ist, die immer wiederkehrenden Spring Cloud Stream Konfigurationen
an einer zentralen Stelle zu lösen und eine Api bereitzustellen, um diese zu nutzen.
Dadurch muss ein Integrations-Developer nicht mehr in die Tiefe der Spring Cloud Stream Konfigurationen einsteigen und
kann sich auf die Implementierung der Integration fokussieren.

Die Bibliothek stellt für das Versenden von Nachrichten eigene APIs bereit, die Nachrichten an einen Message Broker senden.
Zusätzlich stellt die Bibliothek auch einen Spring Cloud Stream `RoutingCallback` für das Konsumieren und Weiterleiten von Nachrichten bereit (Function Routing).

## Verwendung

Die **DigiWF Message** Bibliothek stellt die MessageApi bereit, die verwendet wird, um Nachrichten zu versenden.

Zusätzlich werden APIs für wiederkehrende Nachrichten bereitgestellt, die wiederum auf der MessageApi aufbauen.
Hierfür haben wir die ProcessApi und die ErrorApi geschaffen.
Die ProcessApi kann verwendet werden, um in DigiWF Prozesse zu starten, Messages an Prozesse zu korrelieren und Fehlerbehandlung durchzuführen.
Die ErrorApi stellt die Exceptions `BpmnError` für fachliche Fehler und `IncidentError` für technische Fehler bereit, die geworfen und in der Anwendung abgefangen werden können.
Zusätzlich werden wie auch bei der ProcessApi Methoden bereitgestellt, um Nachrichten an die entsprechenden Destinations (Zieltopics) zu senden.

Die Destinations für die unterschiedlichen Aktionen können über die `application.yml` konfiguriert werden (siehe [Konfiguration](#konfiguration)).

> Usage Examples finden Sie im [Example-Module in Github](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-message/digiwf-message-example/).

### MessageApi

Die MessageApi stellt die Methode `sendMessage` bereit, die verwendet werden kann, um eine Nachricht an eine bestimmte *Destination* zu senden.
Eine Message besteht aus einem `payload` und `headers`.
Der Payload enthält die Daten, die übermittelt werden sollen.
Die Headers sind ein Key, Value Paar, das zusätzliche Informationen zu der Nachricht enthält.

> Bei DigiWF verwenden wir Spring Cloud Stream, um Nachrichten an Kafka (Message Broker) zu senden.
> Der Payload ist hierbei das Event, das an Kafka gesendet wird.
> Die Headers enthalten wichtige Informationen, wie beispielsweise die Prozessinstanz Id, den Type des Events, usw.

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

Die `ProcessApi`-Schnittstelle stellt Methoden zum Starten von Prozessen und Korrelieren von Nachrichten in Prozessen bereit.
Im Hintergrund nutzt die ProcessAPI die MessageApi, um die Nachrichten an die entsprechenden Destinations zu senden.
Die Destinations für die unterschiedlichen Aktionen können über die `application.yml` konfiguriert werden.

**Usage Example**
```java
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessApi processApi;

    public void sendMessages() {
        // Starten Sie einen neuen Prozess mit dem Schlüssel "meinProzess" und einigen Variablen
        processApi.startProcess("meinProzess", new HashMap<String, Object>());

        // Starten Sie einen neuen Prozess mit dem Schlüssel "meinProzess", einigen Variablen und einem fileContext
        processApi.startProcess("meinProzess", new HashMap<String, Object>(), "fileContext");

        // Korrelieren Sie eine Nachricht mit der Prozessinstanz-ID und einigen Variablen
        processApi.correlateMessage("123", "meineNachricht", new HashMap<String, Object>());
    }
}
```

### ErrorApi

Die ErrorApi definiert Methoden zum Behandeln von Fehlern in einem BPMN-Prozess.
Dabei wird zwischen fachlichen (`BpmnError`) und technischen Fehlern (`IncidentError`) unterschieden.
BpmnError können im Prozess abgefangen werden und verarbeitet werden. Hingegen ein IncidentError führt zu einem Incident im Prozess.

Die ErrorApi stellt Exceptions für die beiden Fehler bereit, die geworfen und in der Anwendung abgefangen werden können.
Für die Fehlerbehandlung werden ebenfalls Methoden bereitgestellt, die Nachrichten an die konfigurierten Destinations senden.

**Usage Example**
```java
import java.util.Map;

@RequiredArgsConstructor
public class Example {
    private final ErrorApi errorApi;

    public void sendErrorMessages() {
        this.errorApi.handleBpmnError("ProcessInstanceID", "400", "Foo is not bar");

        this.errorApi.handleIncidentError("ProcessInstanceId", "The origin message", "The error message");
    }

    public void sendErrorMessagesWithException() {
        final Map<String, Object> originMessageHeaders = Map.of(
                "digiwf.messagename", "theMessageName",
                "digiwf.processinstanceid", "theProcessInstanceId"
        );

        try {
            throw new BpmnError("400", "Foo is not bar");
        } catch (final BpmnError bpmnError) {
            this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
        }

        try {
            throw new IncidentError("Foo is not bar");
        } catch (final IncidentError incidentError) {
            this.errorApi.handleIncident(message.getHeaders(), incidentError);
        }
    }
}
```

## Spring Cloud Stream Komponenten

Die DigiWF Message Bibliothek stellt Spring Cloud Stream Komponenten bereit, die verwendet werden können, um Nachrichten an Kafka zu senden und zu empfangen.
Hierfür werden Event Emitter (`Sinks`) und ein Function Router (`RoutingCallback`) bereitgestellt.

### Event Emitter

Es wird ein Event Emitter `sendMessage` bereitgestellt. Dieser wird verwendet, um Nachrichten an die entsprechenden Destinations zu senden.
Intern verwendet die MessageApi den `spring.cloud.stream.sendto.destination` Header, der ausgehende Nachrichten automatisch an das Topic, das als Destination angegeben wurde, sendet.

Jedoch muss, damit Spring Cloud Stream Nachrichten versenden kann ein ausgehender Channel konfiguriert werden.
Hierfür empfiehlt es sich, die `spring.cloud.stream.bindings.sendMessage-out-0.destination` Property zu setzen und
unter `spring.cloud.function.definition` die Funktion `sendMessage` zu definieren.

### Function Router

Neben dem Versenden von Nachrichten über die Event Emitter, können auch Nachrichten über den Function Router empfangen werden.
Hierfür stellen wir einen `RoutingCallback` bereit, der die eingehenden Nachrichten anhand des Headers `type` an die Consumer der Anwendung routed.
Das Mapping zwischen dem Header `type`, der aus den eingehenden Nachrichten ausgelesen wird und den Consumer Funktionen wird in der `application.yml` über die Property `de.muenchen.oss.digiwf.message.typeMappings` konfiguriert.

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
| de.muenchen.oss.digiwf.message.incidentDestination         | Destination to redirect incidents to (e.g. Kafka Topic)                           |
| de.muenchen.oss.digiwf.message.technicalErrorDestination   | Destination to redirect technical errors a.k.a. bpmn errors to (e.g. Kafka Topic) |
| de.muenchen.oss.digiwf.message.correlateMessageDestination | Destination to send correlate messages to (e.g. Kafka Topic)                      |
| de.muenchen.oss.digiwf.message.startProcessDestination     | Destination to send start process messages to (e.g. Kafka Topic)                  |
| de.muenchen.oss.digiwf.message.typeMappings                | Mapping of message types to consumer function names                               |

## Anpassbarkeit

Für die ErrorApi, ProcessApi und MessageApi stellen wir eine Standardimplementierung bereit, die auf Spring Cloud Stream basiert.
Möchte man diese Implementierung ändern kann man die entsprechenden Schnittstellen implementieren und als Bean bereitstellen.

Ein Beispiel für eine MessageApi implementierung, die lediglich die Nachrichten logged findet sich [in unserem Example](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-message/digiwf-message-example/src/main/java/io/muenchendigital/digiwf/message/example/adapter/NoStreamingMessageAdapter.java).
