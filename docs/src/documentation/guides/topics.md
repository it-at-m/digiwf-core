# Topics


Die DigiWF-Anwendungen kommunizieren mit einem `Event Bus`, um Ereignisse auszutauschen, die innerhalb der DigiWF-Plattform auftreten.
Intern verwendet die DigiWF-Anwendung Spring Cloud Stream als Abstraktionsschicht, um mit dem "Event Bus" zu interagieren. Daher ist der `Event Bus` austauschbar, solange ein Spring Cloud Stream Binder existiert.

In diesem Artikel erklären wir die Namenskonventionen für unsere Themen und listen die derzeit existierenden Themen auf.

## Naming conventions für Event Bus Topics

```
<prefix>-<domain>-<usage-context>-<environment>
```

Jeder DigiWF Topic-name besteht aus diesen 3 Teilen:

- **prefix** wird verwendet, um die Themen einer bestimmten Anwendung zuzuordnen. Im Zusammenhang mit DigiWF ist dieses Präfix normalerweise `dwf`.
- **domain** Jedes DigiWF-Thema gehört zu einer bestimmten Domäne. Der Name der Domäne kann dem Anwendungsnamen ähnlich sein.
- **usage-context** Wenn eine Domain mehr als 1 Thema hat, wird ein zusätzlicher `<usage context>` zum Namen des Themas hinzugefügt. Der `<usage context>` ist optional.
- **environment** ist das Suffix, das die Umgebung beschreibt, in der die Anwendungen laufen.

Examples:

- `dwf-cocreation-ENV` ist das Haupttopic der Co-Creation-Plattform. An dieses Thema werden Deploymentevents gesendet.
- `dwf-cocreation-deploy-ENV` ist das zweite Topic der Co-Creation-Plattform. An dieses Topic werden Deployment-Success-Events gesendet.

## Verfügbare Topics

Derzeit existieren die Umgebungen `dev`, `test`, `demo` und `local-01`. `dev`, `test` und `demo` sind unsere CI/CD-Stages und `local-01` wird für die Entwicklung verwendet.

### Domäne digiwf-engine

Die digiwf-engine verwendet den Spring Cloud Stream Function Router, um Ereignisse basierend auf dem `type` Header an die entsprechenden Spring Cloud Funktionen weiterzuleiten.
Daher benötigt jede Nachricht, die an `dwf-digiwf-engine-<ENV>` gesendet wird, den Header `type`.

```
dwf-digiwf-engine-<ENV>
```

Die folgenden Werte für den Header `type` werden derzeit unterstützt:

| Header Type         | Payload Type            | Beschreibung                                                                                                                                   |
|---------------------|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| correlateMessageV01 | `CorrelateMessageTOV01` | Verwenden Sie `CorrelateMessageTOV01` als Wert für den Header type, um eine Nachricht mit einer Prozessinstanz zu korrelieren.                 |
| startProcessV01     | `StartInstanceTOV01`    | Verwenden Sie `startProcessV01` als Wert für den Header type, um eine neue Prozessinstanz mit dem Prozessschlüssel und den Daten zu starten.   |


> **Anmerkung:**
> Wir benutzen springwolf, um asyncapi Dokumentationen zu generieren. Schauen Sie sich die asyncapi-Dokumentation der Dienste unter */springwolf/asyncapi-ui.html* an.

### Domäne cocreation

Prozesse, Entscheidungstabellen und JSON-Schema-Formulare, die in der Cocreation-Plattform entwickelt werden, werden mit einem Deployment-Event, das an das Topic `dwf-cocreation-<ENV>` gesendet wird, in der digiwf-Engine deployed.

```
dwf-cocreation-<ENV>
```

Der digiwf-engine Service ist ein Consumer des Themas `dwf-cocreation-<ENV>` und stellt die Artefakte (bpmn-, dmn- oder json-Form) aus eingehenden Bereitstellungsereignissen für die camunda-Engine bereit.
Die digiwf-engine verwendet den Spring Cloud Stream Function Router, um Events basierend auf dem Header `type` an die entsprechenden Spring Cloud Funktionen weiterzuleiten.

Die folgenden Werte für den Header `type` werden derzeit unterstützt:

| Header Type    | Payload Type            | Beschreibung                                                                                                                   |
|----------------|-------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| deploy         | `DeploymentEvent`       | Verwenden Sie `deploy` als Wert für den Header type, um Prozesse und Entscheidungstabellen in der digiwf-Engine einzusetzen.   |
| deploySchema   | `SchemaDeploymentEvent` | Verwenden Sie `deploySchema` als Wert für den Header type, um JSON-Schema-Formulare an die digiwf-Engine zu übergeben.         |

```
dwf-cocreation-deploy-<ENV>
```

Nach jedem Deployment sendet die digiwf-engine ein Deployment-Status-Ereignis an das Topic `dwf-cocreation-deploy-<ENV>`, das die Abonnenten des Topics über den Erfolg oder Misserfolg des Deployments informiert.

> **Anmerkung:**
> Wir benutzen springwolf, um asyncapi Dokumentationen zu generieren. Schauen Sie sich die asyncapi-Dokumentation der Dienste unter */springwolf/asyncapi-ui.html* an.

### Domäne digiwf-task

Die digiwf-task verwendet zwei topics um die User Tasks oder Daten zu empfangen. Diese sind:

```
dwf-taskmanagement-tasks-<STAGE>
dwf-taskmanagement-data-<STAGE>
```

