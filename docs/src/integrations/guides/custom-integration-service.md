# Eigene Integration erstellen

Um ein Drittsystem an DigiWF anzubinden, muss eine Integration erstellt werden.
Hierfür haben wir ein [Integrationskonzept](../concept/integration-service.md) definiert, auf dessen Basis die Umsetzung einer Integration
beschrieben wird.

> Zur Vereinfachung der Integrationsentwicklung haben wir die Bibliothek [digiwf-message](../../documentation/libs/digiwf-message)
> entwickelt.

Ein Beispiel für eine Integration ist in
unserem [Github Repository](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration) hinterlegt.

## Anwendungsaufbau

Zuerst muss die Grundstruktur der Integration erstellt werden. Hierbei setzen wir auf Spring Boot Starter Anwendungen,
damit diese bei Bedarf genutzt, erweitert und in eigene Anwendungen eingebunden werden können. Hierfür haben wir
folgende Struktur mit einem **core** Modul, einem **starter** Modul und einer **example** Anwendung vorgesehen.
Zusätzlich kann eine **service** Anwendung erstellt werden, die bereits vorkonfiguriert ist und direkt verwendet werden
kann.

Im **core** Modul wird die Businesslogik der Integration entwickelt. Das **starter** Modul stellt eine Spring
Autoconfiguration bereit, die die Spring Beans des **core** Moduls zur Verfügung stellt und Configuration Properties
bereitstellt. Das **example** Modul dient als Beispiel, um die Verwendung des Starters zu demonstrieren. Die **service**
Anwendung ist bereits vorkonfiguriert und kann direkt verwendet werden.

## Nachrichtenverarbeitung

Die Integration wird über einen Event Broker (z.B. Kafka) angesprochen. Für das Messaging haben wir bereits die
Bibliothek [digiwf-message](../../documentation/libs/digiwf-message) entwickelt, die bereits einige Spring Cloud Stream Komponenten
bereitstellt, um die Anwendung mit dem Event Broker zu verbinden.

Zunächst muss die **digiwf-message** Bibliothek als Dependency im **core** Modul eingebunden werden.

```xml

<dependency>
    <groupId>de.muenchen.oss.digiwf</groupId>
    <artifactId>digiwf-message-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

Die digiwf-message Bibliothek stellt als Dependency Spring Cloud Stream bereit. Mithilfe von Spring Cloud Stream können
wir Nachrichten aus einem Message Broker empfangen und verarbeiten. Spring Cloud Stream verfügt über
eine [Routing-Funktionalität](https://github.com/spring-cloud/spring-cloud-stream/blob/main/docs/modules/ROOT/pages/spring-cloud-stream/event-routing.adoc#using-application-properties),
die es ermöglicht, eingehende Nachrichten anhand eines Message Headers an den entsprechenden Spring Cloud Stream
Consumer weiterzuleiten. Demnach muss die Anwendung nur noch die Consumer implementieren, die die Nachrichten vom Event
Broker empfangen und verarbeiten.

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

Damit die eingehenden Nachrichten an den Consumer `exampleIntegration` weitergeleitet werden, muss in
der `application.yml` der Integration folgende Konfiguration angegeben werden. Anschließend werden alle Messages mit dem
Header `type=exampleIntegration` an den Consumer weitergeleitet.

```yaml
spring:
  cloud:
    stream:
      bindings:
        functionRouter-in-0:
          group: "consumer-group-der-integration"
          destination: "topic-der-integration"
```

Neben dem `functionRouter` muss auch der Consumer der Integration in der `application.yml` definiert werden.

## Correlate Message

Nachdem die eingehende Nachricht von der Integration erfolgreich verarbeitet wurde, muss die Integration der Engine noch
eine Rückmeldung geben, dass der Prozess fortgesetzt werden kann. Hierfür muss die Integration an die digiwf-engine eine
Correlate Message senden.

In der `ProcessApi` der digiwf-message Bibliothek ist bereits eine Methode `correlateMessage` implementiert, die von der
Integration aufgerufen werden muss.

```java
processApi.correlateMessage("processInstanceId","messageName",new HashMap<String, Object>());
```

Die `correlateMessage` Methode kann am einfachsten in den `exampleIntegration` Consumer integriert werden.

```java

@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            final String processInstanceId = message.getHeaders().get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)
                    .toString();
            final String type = message.getHeaders().get(MessageConstants.TYPE).toString();
            final String integrationName = message.getHeaders().get(MessageConstants.DIGIWF_INTEGRATION_NAME)
                    .toString();

            // do something with message

            // correlate message
            processApi.correlateMessage(processInstanceId, type, integrationName, new HashMap<String, Object>());
        };
    }
}
```

> Wichtig ist hierbei, dass die `processInstanceId` und `messageName` der eingehenden Message und des ausgehenden
> CorrelateMessage Events identisch sind. Ansonsten kann die Message keinem Prozess zugeordnet werden.

## Fehlerbehandlung implementieren

Wir unterstützen unterschiedliche Möglichkeiten, um Fehler in der Integration an den Prozess zurückzuspielen. Fachliche
Fehler, die im Prozess verarbeitet werden können, werden als `BpmnError` geworfen. Technische Fehler, die einen Incident
erzeugen sollen, werden als `IncidentError` geworfen. Weitere Details zum Error Handling sind im Konzept 
[Fehlerbehandlung Integrationen](../concept/error-handling.md) beschrieben.

Um die Fehlerbehandlung für `BpmnError`s und `IncidentError`s zu implementieren, können die Consumer wie folgt um
try-catch Blöcke erweitert werden.

```java

@RequiredArgsConstructor
public class MessageProcessor {

    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    @Bean
    public Consumer<Message<ExampleDto>> exampleIntegration() {
        return message -> {
            try {
                final String processInstanceId = message.getHeaders().get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)
                        .toString();
                final String type = message.getHeaders().get(MessageConstants.TYPE).toString();
                final String integrationName = message.getHeaders().get(MessageConstants.DIGIWF_INTEGRATION_NAME)
                        .toString();

                // do something with message

                // correlate message
                this.processApi.correlateMessage(processInstanceId, type, integrationName,
                        new HashMap<String, Object>());
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

Zusätzlich zur Fehlerbehandlung kann die DLQ- und Retry-Konfiguration für die Consumer in der `application.yml`
definiert werden. Die verschiedenen Konfigurationsoptionen sind im [Fehlerbehandlung Integrationen](../concept/error-handling.md) Konzept beschrieben.

### BpmnError werfen

Um einen BpmnError zu werfen, muss der `BpmnError` mit dem Fehlercode und der Fehlermeldung erzeugt werden. BpmnErrors
können verwendet werden, wenn beispielsweise die Validierung der eingehenden Nachricht fehlschlägt.

```java
throw new BpmnError("errorCode","errorMessage");
```

Alle `BpmnError`s sollten dokumentiert werden, damit die Prozessmodellierer wissen, welche Fehler auftreten können,
damit sie diese im Prozess berücksichtigen können.

### IncidentError werfen

Um einen IncidentError zu werfen, muss der `IncidentError` mit dem Fehlercode und der Fehlermeldung erzeugt werden.

```java
throw new IncidentError("errorMessage");
```

Ein `IncidentError` wird zu einem Incident umgewandelt, der händisch aufgelöst werden muss. Dieser Fehler kann verwendet
werden, wenn schwerwiegendere technische Fehler auftreten, die nicht automatisch behoben werden können.

## Anwendung konfigurieren

Wie bereits in der Dokumentation der [digiwf-message](../../documentation/libs/digiwf-message#konfiguration) Bibliothek beschrieben ist,
muss die Anwendung konfiguriert werden, damit die Integration mit dem Event Broker verbunden werden kann.

Zu großen Teilen kann untenstehende Konfiguration als `application.yml` übernommen werden. Es müssen jedoch noch die
Topics, die Streaming Group und die TypeMapping angepasst werden.

> In nachfolgender Beispiel Konfiguration werden das Topic der digiwf-engine und des digiwf-connectors über Umgebungsvariablen definiert.
> Diese Variablen können entweder gesetzt werden oder sie müssen durch den Wert ersetzt werden.
> Es empfielt sich neben den bereits definierten Umgebungsvariablen auch eigene Variablen zu definieren und zu verwenden.

```yaml
spring:
  cloud:
    stream:
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
        deadLetterQueueDestination: "${KAFKA_TOPICS_CONNECTOR_DLQ}"
```

Im obigen Beispiel wird die Konfiguration des Binders bewusst ausgelassen, da diese vom verwendeten Binder abhängig ist.
Eine Konfiguration für den Kafka Binder kann dem [digiwf-integration-example](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration) entnommen werden.

## Anwenderdokumentation erstellen

Schließlich sollte die Integration noch dokumentiert werden, damit die User diese in ihren Prozessen verwenden können.
Hierbei sollten einerseits Element Templates für die Modellierung bereitgestellt und andererseits die
technischen (`IncidentError`) sowie fachlichen Fehler (`BpmnError`) dokumentiert werden.