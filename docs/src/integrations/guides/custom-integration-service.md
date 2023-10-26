# Eigene Integration erstellen

Um ein Drittsystem an DigiWF anzubinden, muss eine Integration erstellt werden.
Hierfür haben wir eine [Integrationskonzepts](../concept/integration-service.md) definiert, auf dessen Basis 
die Umsetzung einer Integration beschrieben wird.

> Wir haben die Bibliothek [digiwf-message](../../documentation/libs/digiwf-message) entwickelt, um die Integrationsentwicklung zu vereinfachen.

Ein Beispiel Integration ist in unserem [Github Repository](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration) hinterlegt.

## Anwendungsaufbau

Zuerst muss die Grundstruktur der Integration erstellt werden.
Hierbei setzen wir für gewöhnlich auf Spring Boot Starter Anwendungen, damit diese bei Bedarf genutzt, erweitert und in eigene Anwendungen eingebunden werden können.
Hierfür haben wir folgende Struktur mit einem **core** Modul, einem **starter** Modul und einer **example** Anwendung vorgesehen.
Zusätzlich kann eine **service** Anwendung erstellt werden, die bereits vorkonfiguriert ist und direkt verwendet werden kann.

Im **core** Modul wird die Businesslogik der Integration entwickelt.
Im **starter** Modul wird eine Spring Autoconfiguration erstellt, die die Spring Beans des **core** Moduls zur Verfügung stellt
und Configuration Properties bereitstellt.
Das **example** Modul dient als Beispiel, um die Verwendung des Starters zu demonstrieren.
Die **service** Anwendung ist bereits vorkonfiguriert und kann direkt verwendet werden.

## Nachrichtenverarbeitung

Die Integration wird über einen Event Broker (z.B. Kafka) angesprochen.
Für das Messaging haben wir bereits die Bibliothek [digiwf-message](../../documentation/libs/digiwf-message) entwickelt,
die bereits einige Spring Cloud Stream Komponenten bereitstellt, um die Anwendung mit dem Event Broker zu verbinden.

Dementsprechend muss zuerst die **digiwf-message** Bibliothek als Dependency in das **core** Modul eingebunden werden.

```xml
    <dependency>
        <groupId>de.muenchen.oss.digiwf</groupId>
        <artifactId>digiwf-message-starter</artifactId>
        <version>${project.version}</version>
    </dependency>
```

Diese Bibliothek stellt bereits einen Function Router zur Verfügung, der die eingehenden Nachrichten anhand des Message Headers
`type` an den in den *typeMappings* konfigurierten Consumer weiterleitet.
Demnach muss die Anwendung nur noch die Consumer implementieren, die die Nachrichten vom Event Broker empfangen und verarbeiten.

```java
public class MessageProcessor {

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            // do something with message
        };
    }   
}
```

Damit an den obigen `exampleIntegration` Consumer die Nachrichten weitergeleitet werden, muss in den typeMappings der digiwf-message lib
die folgende Konfiguration angegeben werden. Anschließend werden alle Messages mit dem Header `type=exampleIntegration` an den Consumer weitergeleitet.

```yaml
io:
  muenchendigital:
    digiwf:
      message:
        typeMappings:
          exampleIntegration: "exampleIntegration"
```

## Correlate Message

Nachdem die eingehende Nachricht von der Integration erfolgreich verarbeitet wurde, muss die Integration der Engine noch 
eine Rückmeldung geben, dass der Prozess fortgesetzt werden kann.
Hierfür muss die Integration an die digiwf-engine eine Correlate Message senden.

In der `ProcessApi` der digiwf-message lib ist bereits eine Methode `correlateMessage` implementiert, die von der Integration
aufgerufen werden muss.

```java
processApi.correlateMessage("processInstanceId", "messageName", new HashMap<String, Object>());
```

Am einfachsten ist es die `correlateMessage` Methode in den `exampleIntegration` Consumer zu integrieren.

```java
@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    
    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            final String processInstanceId = message.getHeaders().get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID).toString();
            final String messageName = message.getHeaders().get(MessageConstants.DIGIWF_MESSAGE_NAME).toString();
            
            // do something with message
            
            // correlate message
            processApi.correlateMessage(processInstanceId, messageName, new HashMap<String, Object>());
        };
    }   
}
```

> Wichtig ist hierbei, dass die `processInstanceId` und `messageName` der eingehenden Message und des ausgehenden CorrelateMessage Events
> identisch sind. Ansonsten kann die Message keinem Prozess zugeordnet werden.

## Fehlerbehandlung implementieren

Wir unterstützen unterschiedliche Möglichkeiten, um Fehler in der Integration an den Prozess zurückzuspielen.
Fachliche Fehler, die im Prozess verarbeitet werden können, werden als `BpmnError` geworfen.
Technische Fehler, die einen Incident erzeugen sollen werden als `IncidentError` geworfen.
Alle anderen Fehler werden von Spring Cloud Stream behandelt (je nach Konfiguration entweder als DLQ und/oder mit mehrfachem Retry).
Weitere Details zum Error Handling sind im Konzept [Fehlerbehandlung Integrationen](../concept/error-handling.md) beschrieben.

Um die Fehlerbehandlung für die `BpmnError`s und `IncidentError`s zu implementieren, können die Consumer wie folgt um try catch Blöcke erweitert werden.

```java
@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    
    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            try {
                final String processInstanceId = message.getHeaders().get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID).toString();
                final String messageName = message.getHeaders().get(MessageConstants.DIGIWF_MESSAGE_NAME).toString();

                // do something with message

                // correlate message
                this.processApi.correlateMessage(processInstanceId, messageName, new HashMap<String, Object>());
            } catch (final BpmnError bpmnError) {
                // handle bpmn errors
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                // handle incident errors
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }
}
```

Zusätzlich zur Fehlerbehandlung kann DLQ und Retry Konfiguration für die Consumer in der `application.yml` definiert werden.
Die verschiedenen Konfigurationsoptionen sind im [Fehlerbehandlung Integrationen](../concept/error-handling.md) Konzept beschrieben.

### BpmnError werfen

Um einen BpmnError zu werfen, muss der `BpmnError` mit dem Fehlercode und der Fehlermeldung erzeugt werden.
BpmnErrors können verwendet werden, wenn beispielsweise die Validierung der eingehenden Nachricht fehlschlägt.

```java
throw new BpmnError("errorCode", "errorMessage");
```

Alle `BpmnError`s sollten dokumentiert werden, damit die Prozessmodellierer wissen, welche Fehler auftreten können, damit sie diese im Prozess berücksichtigen können.

### IncidentError werfen

Um einen IncidentError zu werfen, muss der `IncidentError` mit dem Fehlercode und der Fehlermeldung erzeugt werden.

```java
throw new IncidentError("errorMessage");
```

Ein `IncidentError` wird zu einem Incident umgewandelt, der händisch aufgelöst werden muss.
Dieser Fehler kann verwendet werden, wenn schwerwiegendere technische Fehler auftreten, die nicht automatisch behoben werden können.

## Anwendung konfigurieren

Wie bereits in der Dokumentation der [digiwf-message](../../documentation/libs/digiwf-message#konfiguration) Bibliothek beschrieben ist,
muss die Anwendung konfiguriert werden, damit die Integration mit dem Event Broker verbunden werden kann.

Zu großen Teilen kann untenstehende Konfiguration als `application.yml` übernommen werden. Es müssen jedoch noch die Topics,
die Streaming Group und die TypeMapping angepasst werden.

> In nachfolgender Beispiel Konfiguration werden das Topic der digiwf-engine und des digiwf-connectors über Umgebungsvariablen definiert.
> Diese Variablen können entweder gesetzt werden oder sie müssen durch den Wert ersetzt werden.
> Es empfielt sich neben den bereits definierten Umgebungsvariablen auch eigene Variablen zu definieren und zu verwenden.

```yaml
spring:
  cloud:
    function:
      definition: functionRouter;sendMessage;
    stream:
      function:
        routing:
          enabled: 'true'
      bindings:
        functionRouter-in-0:
          group: "consumer-group-der-integration"
          destination: "topic-der-integration"
        sendMessage-out-0:
          destination: "${KAFKA_TOPIC_ENGINE}"
[...]
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "${KAFKA_TOPICS_CONNECTOR_INCIDENT}"
        bpmnErrorDestination: "${KAFKA_TOPICS_CONNECTOR_BPMNERROR}"
        correlateMessageDestination: "${KAFKA_TOPIC_ENGINE}"
        typeMappings:
          exampleIntegration: "exampleIntegration"
```

Im obigen Beispiel wird die Konfiguration des Binders bewusst ausgelassen, da diese vom verwendeten Binder abhängig ist.
Eine Konfiguration für den Kafka Binder kann dem [digiwf-integration-example](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration) entnommen werden.

## Anwender Dokumentation erstellen

Schlussendlich sollte die Integration noch dokumentiert werden, damit die User diese in ihren Prozessen verwenden können.
Hierbei sollten einerseits Element Templates für die Modellierung bereitgestellt und die technischen (`IncidentError`) sowie fachlichen Fehler (`BpmnError`) dokumentiert werden.
