# Eigene Integration erstellen

> Die Integration digiwf-example-integration in unserem [Github Repository](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration) kann als Beispiel dienen.

## Grundlagen

Eine DigiWF Integration ist eine Spring Boot Anwendung, die ein drittsystem an DigiWF anbindet.
Ein Aufruf der Integration erfolgt über das External Task Pattern von Camunda.
External Tasks werden in DigiWF über das Spring Cloud Stream Framework implementiert.

> Für die Integrationsentwicklung haben wir die Bibliothek [digiwf-integration-lib](/documentation/libs/digiwf-integration-lib) entwickelt,
> um die Anbindung an Spring Cloud Stream zu vereinfachen und technische Komplexitäten zu verringern.

#### 1. Spring Boot Anwendung erstellen

#### 2. DigiWF Integration Starter Bibliothek einbinden

```xml
        <dependency>
            <groupId>io.muenchendigital.digiwf</groupId>
            <artifactId>digiwf-example-integration-starter</artifactId>
            <version>${project.version}</version>
        </dependency>
```

#### 3. DigiWF Integration erstellen

Für eine DigiWF Integration muss eine Klasse erstellt werden, die als Bean bereitgestellt wird.
In dieser Klasse muss eine Methode mit der Annotation `@DigiwfIntegration` implementiert werden.
Der Annotation wird der `type` Parameter übergeben, der dafür verantwortlich ist, um eingehende Nachrichten an diese Methode weiterzuleiten.
Hierbei werden eingehende Nachrichten von Spring Cloud Stream an die Methode weitergeleitet, die den gleichen `type` Parameter als Message Header haben.

Die Daten, die von dieser Methode zurückgegeben werden, werden als Correlate Message an die Prozessinstanz zurückgespielt,
in dem eine Correlate Message Nachricht in Kafka geschrieben wird.

Auftretende Fehler können in dieser Methode geworfen werden und werden dann als Incident bzw. Technical Error behandelt.
Weitere Details zum Error Handling sind in der [digiwf-integration-lib](/documentation/libs/digiwf-integration-lib/) beschrieben.

```java
@Component
public class ExampleIntegration {

    @DigiwfIntegration(type = "example-integration")
    public Map<String, String> exampleIntegration(final Map<String, Object> event) {
        // obtain the process instance id and the message name from the event
        final String processInstanceId = event.get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID).toString();
        final String messageName = event.get(StreamingHeaders.DIGIWF_MESSAGE_NAME).toString();
        // you may also want to do some data mapping here

        // Add your business logik here
        
        // What you return will be correlated to the process instance and the message name
        return Map.of("status", "success", "message", "Some message ...");
    }
}
```

Neben der Spring Cloud Stream Schnittstelle, die bereits in der `digiwf-integration-lib` implementiert ist, können weitere
Schnittstellen (wie z.B. ein REST Controller) bereitgestellt werden.
Diese sollten jedoch sparsam verwendet werden und müssen selbst in der Integration implementiert werden.

> Intern wird die [digiwf-message](/documentation/libs/digiwf-message/) Bibliothek verwendet, um Nachrichten via Spring Cloud Stream zu versenden.
> Diese Bibliothek kann auch direkt in der Integration verwendet werden, um zusätzlich Nachrichten zu versenden.
> Es müssen keine weiteren Bibliotheken hierfür eingebunden werden.

#### 4. Spring Cloud Stream Konfigurationen vornehmen

Nachfolgende Konfiguration der `application.yml` ist notwendig, um die Integration mit dem DigiWF Message Broker zu verbinden.

```yaml
spring:
  cloud:
    stream:
      bindings:
        receiveMessages-in-0:
          group: "digiwf-integration-example"
          destination: "digiwf-example-integration"
        sendMessage-out-0:
          destination: "digiwf-example-integration"
      kafka:
        binder:
          brokers: "${KAFKA_BOOTSTRAP_SERVER:localhost}:${KAFKA_BOOTSTRAP_SERVER_PORT:29092}"
          producerProperties:
            value:
              serializer: org.springframework.kafka.support.serializer.JsonSerializer
            key:
              serializer: org.springframework.kafka.support.serializer.JsonSerializer
          consumerProperties:
            auto:
              offset:
                reset: latest
            value:
              deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
            key:
              deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
          configuration:
            security:
              protocol: "${KAFKA_SECURITY_PROTOCOL:PLAINTEXT}"
    function:
      definition: receiveMessages;sendMessage;
  kafka:
    consumer:
      properties:
        spring:
          json:
            trusted:
              packages: "*"
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "digiwf-example-integration-incident"
        technicalErrorDestination: "digiwf-example-integration-technical-error"
        correlateMessageDestination: "digiwf-example-integration-correlate-message"
```

Die Einstellungen unter `io.muenchendigital.digiwf.message` sind insbesondere für die Fehlerbehandlung sowie das abschließen von External Tasks wichtig.
In diesen werden die Topics für die verschiedenen Fehlermeldungen und die Topics für die Correlate Message konfiguriert.

> Für weitere Details hierzu siehe [digiwf-message](/documentation/libs/digiwf-message/).

#### 5. Element Template erstellen

Zu guter Letzt muss noch ein Element Template erstellt werden, dass in Prozessen verwendet werden kann, um die Integration
mit dem External Task Pattern aufzurufen.

## Empfohlene Anwendungsstruktur

Wir implementieren Digiwf Integrationen als Spring Boot Starter mit einem **core**, einem **starter** Modul, einer **example** Anwendung und einer optionalen **service** Anwendung.

Im **core** Module wird die Integration entwickelt, in dem die Businesslogik implementiert und APIs angebunden werden.
Das **starter** Modul verwendet den **core** und stellt Spring Beans zur Verfügung, die in anderen Anwendungen verwendet werden können.
Die **example** Anwendung demonstriert die Verwendung des Starters und kann zum Testen verwendet werden.
Die **service** Anwendung ist bereits vorkonfiguriert und kann direkt verwendet werden. Diese Service Anwendung ist üblicherweise als Docker Image im  [dockerhub](https://hub.docker.com/u/itatm) veröffentlicht. Eine solche Service Anwendung ist nur bei generische Integrationen sinnvoll.

**Struktur Core Module**

Für die Struktur des **core** Moduls gibt es keine festen Vorgaben, außer dass der Code verständlich und einfach wartbar sein sollte.
Hierfür ist es empfehlenswert eine klassische Schichtenarchitektur oder eine zu Hexagonale Architektur verwenden.
Die Wahl sollte hier jedoch je nach Anwendungsfall und Integration getroffen werden.
