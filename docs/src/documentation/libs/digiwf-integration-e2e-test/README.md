# DigiWF Integration E2e Test Library

Mithilfe der DigiWF Integration E2e Test Library können Integrationen End-To-End getestet werden.
Hierfür wird ein Embedded-Kafka gestartet und über Spring Cloud Stream eine Verbindung zu diesem hergestellt.
Anschließend kann die Integration in End-To-End Tests getestet werden, in dem Nachrichten an den Embedded-Kafka gesendet werden und die Antworten überprüft werden.

Externe Schnittstellen können zudem über Wiremock simuliert werden.

Um dieses Setup zu vereinfachen, stellt die DigiWF Integration E2e Test Library Hilfsklassen, Annotations und Configs zur Verfügung.

## Verwendung

Die Bibliothek muss in der Anwendung als Test Dependency deklariert werden:

```xml
        <dependency>
            <groupId>de.muenchen.oss.digiwf</groupId>
            <artifactId>digiwf-integration-e2e-test-starter</artifactId>
            <version>${latest.digiwf.version}</version>
            <scope>test</scope>
        </dependency>
```

Anschließend kann die Bibliothek in einem End-To-End Test verwendet werden:

```java
@DigiwfE2eTest
class ExampleIntegrationE2eTest {
    private String processInstanceId;

    @Autowired
    private DigiWFIntegrationE2eTestUtility digiWFIntegrationE2eTestUtility;

    @BeforeEach
    void setup() {
        this.processInstanceId = UUID.randomUUID().toString();
    }
    
    @Test
    void testExampleFeature() {
        // create integration input data
        final ExampleIntegrationInputData testData = new ExampleIntegrationInputData();
        final String type = "integrationType";
        
        // run the integration
        final Map<String, Object> payload = this.digiWFIntegrationE2eTestUtility.runIntegration(testData, processInstanceId, type);
        
        // verify the payload with asserts
    }
}
```

Die Annotation `@DigiwfE2eTest` dient als Helper-Annotation, um die Spring-Boot-Test-Annotationen `@SpringBootTest`, `@EmbeddedKafka`, `@ActiveProfiles("itest")` und `@DirtiesContext` zu kombinieren.

> **Hinweis:** Die E2e Tests müssen mit dem Profil `itest` ausgeführt werden.

### Konfiguration

```yaml
spring:
    cloud:
        stream:
            bindings:
                functionRouter-in-0:
                    destination: "dwf-example-integration-test"
```

In der `application-itest.yml` muss die Destination (Kafka Topic) für den Function Router angegeben werden.
Weitere Konfiguration werden direkt über die Bibliothek bereitgestellt.

### Wiremock

Um externe Schnittstellen zu simulieren, kann Wiremock verwendet werden.
Hierfür wird die Methode `setupWiremock` der Klasse `DigiWFIntegrationE2eTestUtility` bereitgestellt.
Diese Methode erwartet als Parameter den Pfad der Schnittstelle und die Antwort, die Wiremock zurückgeben soll.

> **Hinweis:** Aktuell werden nur GET-Requests unterstützt.

```java
@DigiwfE2eTest
@WireMockTest(httpPort = 8089)
class ExampleIntegrationE2eTestWithWiremock {
    private String processInstanceId;

    @Autowired
    private DigiWFIntegrationE2eTestUtility digiWFIntegrationE2eTestUtility;

    @BeforeEach
    void setup() {
        this.processInstanceId = UUID.randomUUID().toString();
    }
    
    @Test
    void testExampleFeature() {
        // create integration input data
        final ExampleIntegrationInputData testData = new ExampleIntegrationInputData();
        final String type = "integrationType";
        
        // setup wiremock
        this.digiWFIntegrationE2eTestUtility.setupWiremock("/url/", "{\"foo\": \"bar\"}");
        
        // run the integration
        final Map<String, Object> payload = this.digiWFIntegrationE2eTestUtility.runIntegration(testData, processInstanceId, type);
        
        // verify the payload with asserts
    }
}
```
