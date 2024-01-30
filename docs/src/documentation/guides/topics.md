# Topics

Die DigiWF-Anwendungen kommunizieren mit einem `Event Bus`, um Ereignisse auszutauschen, die innerhalb der
DigiWF-Plattform auftreten.
Intern verwendet die DigiWF-Anwendung Spring Cloud Stream als Abstraktionsschicht, um mit dem "Event Bus" zu
interagieren. Daher ist der `Event Bus` austauschbar, solange ein Spring Cloud Stream Binder existiert.

In diesem Artikel erklären wir die Namenskonventionen für unsere Themen und listen die derzeit existierenden Themen auf.

## Naming conventions für Event Bus Topics

```
<prefix>-<domain>-<usage-context>-<environment>
```

Jeder DigiWF Topic-name besteht aus diesen 3 Teilen:

- **prefix** wird verwendet, um die Themen einer bestimmten Anwendung zuzuordnen. Im Zusammenhang mit DigiWF ist dieses
  Präfix normalerweise `dwf`.
- **domain** Jedes DigiWF-Thema gehört zu einer bestimmten Domäne. Der Name der Domäne kann dem Anwendungsnamen ähnlich
  sein.
- **usage-context** Wenn eine Domain mehr als 1 Thema hat, wird ein zusätzlicher `<usage context>` zum Namen des Themas
  hinzugefügt. Der `<usage context>` ist optional.
- **environment** ist das Suffix, das die Umgebung beschreibt, in der die Anwendungen laufen.

Examples:

- `dwf-connector-${DIGIWF_ENV}` ist das Topic des DigiWF Connectors, an das die Integrationen Nachrichten senden.
- `dwf-email-${DIGIWF_ENV}` ist das Topic der DigiWF Email Integration

## Verfügbare Topics

Derzeit existieren die Umgebungen `dev`, `test`, `demo`, `processestest`, `processesdemo`, `processeshotfix`
und `local-01`. `dev`, `test` und `demo` sind unsere CI/CD-Stages und `local-01` wird für die Entwicklung verwendet.
Die `process*` Umgebungen werden von Prozessmodellierer genutzt für das Testen ihrer Prozesse.

#### [Task](/documentation/components/tasks)

Die digiwf-task verwendet zwei topics um die User Tasks oder Daten zu empfangen. Diese sind:

```
dwf-taskmanagement-tasks-<STAGE>
dwf-taskmanagement-data-<STAGE>
```

### [Connector](/documentation/components/connector)

Der DigiWF Connector ermöglicht eine nahtlose Verbindung zwischen der Camunda Engine (DigiWF Engine) und den DigiWF
Integrationen.
Der Connector leitet Nachrichten der Engine an die Integrationen weiter und leitet die Antworten der Integrationen an
die Engine zurück.
Wenn hierbei Fehler auftreten wird über die jeweiligen Topics ein BPMN Error oder ein Incident an die Engine
zurückgeleitet.

```
dwf-connector-<ENV>
dwf-connector-bpmnerror-<ENV>
dwf-connector-incident-<ENV>
dwf-connector-<ENV>-dlq
```

### [Integrationen](/integrations/)

Die DigiWF Integrationen sind die Schnittstellen zu den externen Systemen. Sie sind in der Lage, Nachrichten vom
Connector zu empfangen und Nachrichten an den Connector zu senden.
Im Fehlerfall senden die Integrationen BPMN Errors oder Incidents an den Connector zurück.

#### [Address Integration](/integrations/digiwf-address-integration)

Die Address Integration bindet den Address-Service der Stadt München an die DigiWF Plattform an, wodurch Informationen
zu Adressen sowie Straßen abgerufen werden können.

```
dwf-address-<ENV>
```

 Header Type              | Payload Type                 | Beschreibung                                                                                                       |
|--------------------------|------------------------------|--------------------------------------------------------------------------------------------------------------------|
| searchAddressesGermany   | SearchAdressenDeutschlandDto | Dieser Header Typ wird verwendet, um eine Suche nach Adressen im gesamten Bundesgebiet Deutschlands zu initiieren. |
| checkAddressMunich       | CheckAdresseMuenchenDto      | Mit diesem Header Typ wird eine Überprüfung von Adressen in München angefordert.                                   |
| listAddressesMunich      | ListAdressenMuenchenDto      | Dieser Header Typ dient dazu, eine Liste von Adressen in München zu erfragen.                                      |
| listChangesMunich        | ListAenderungenMuenchenDto   | Über diesen Header Typ werden Änderungen an Münchner Adressen abgefragt.                                           |
| searchAddressesMunich    | SearchAdressenMuenchenDto    | Dieser Header Typ wird genutzt, um eine gezielte Adresssuche innerhalb Münchens durchzuführen.                     |
| searchAddressesGeoMunich | SearchAdressenGeoMuenchenDto | Mit diesem Header Typ kann eine geobasierte Suche nach Adressen in München initiiert werden.                       |
| findStreetByIdMunich     | findStreetByIdMunich         | Dieser Header Typ wird verwendet, um eine Straße in München anhand ihrer ID zu finden.                             |
| listStreetMunich         | ListStrassenDto              | Mit diesem Header Typ wird eine Liste von Straßen in München angefordert.                                          |

#### [Alw Integration](/integrations/digiwf-alw-integration)

Das Ziel dieser Bibliothek besteht darin, eine asynchrone Kommunikation mit dem ALW-System zu ermöglichen.

```
dwf-alw-<ENV>
```

| Header Type          | Payload Type            | Beschreibung                                                                                    |
|----------------------|-------------------------|-------------------------------------------------------------------------------------------------|
| getAlwResponsibility | `ResponsibilityRequest` | Dieser Header Typ wird genutzt, um die Zuständigkeit im Rahmen des Ausländerwesens zu erfragen. |

#### [Cosys Integration](/integrations/digiwf-cosys-integration)

Die Cosys Integration ermöglicht die Dokumenterstellung in Cosys.

```
dwf-cosys-<ENV>
```

| Header Type      | Payload Type       | Beschreibung                                                        |
|------------------|--------------------|---------------------------------------------------------------------|
| cosysIntegration | `GenerateDocument` | Dieser Header wird verwendet, um ein Dokument in Cosys zu erzeugen. |

#### [Dms Integration](/integrations/digiwf-dms-integration)

Die Dms Integration ermöglicht die Kommunikation mit dem DMS.

```
dwf-dms-<ENV>
```

| Header Type       | Payload Type         | Beschreibung                                                                                 |
|-------------------|----------------------|----------------------------------------------------------------------------------------------|
| createFile        | CreateFileDto      | Initiiert die Erstellung einer neuen Sachakte.                                               |
| createProcedure   | CreateProcedureDto | Startet den Prozess, einen neuen Vorgang im System anzulegen.                                |
| depositObject     | DepositObjectDto   | Ermöglicht das Archivieren eines Objekts in den Aktenbestand.                                |
| createDocument    | CreateDocumentDto  | Löst die Generierung eines neuen Dokuments aus.                                              |
| updateDocument    | UpdateDocumentDto  | Steuert die Aktualisierung eines bestehenden Dokuments.                                      |
| cancelObject      | CancelObjectDto    | Dient dem Rückzug oder der Stornierung eines Dokuments aus dem DMS.                          |
| readContent       | ReadContentDto     | Ermöglicht das Abrufen und Speichern eines Dokumenteninhalts aus dem DMS in den S3 Speicher. |
| searchFile        | SearchObjectDto    | Unterstützt die gezielte Suche nach einer bestimmten Sachakte im DMS.                        |
| searchSubjectArea | SearchObjectDto    | Führt eine Suche nach spezifischen Akteneinträgen durch.                                     |

#### [Email Integration](/integrations/digiwf-mail-integration)

Die Email Integration ermöglicht das Versenden von Emails.

```
dwf-email-<ENV>
```

| Header Type          | Payload Type | Beschreibung                                          |
|----------------------|--------------|-------------------------------------------------------|
| sendMailFromEventBus | `Mail`       | Dieser Header wird verwendet, um Emails zu versenden. |

#### [S3 Integration](/integrations/digiwf-s3-integration)

Die S3 Integration ermöglicht das Speichern, Lesen und Aktualisieren von Dateien im S3 Speicher.

```
dwf-s3-<ENV>
```

| Header Type        | Payload Type              | Beschreibung                                                                                                            |
|--------------------|---------------------------|-------------------------------------------------------------------------------------------------------------------------|
| createPresignedUrl | `CreatePresignedUrlEvent` | Dieser Header wird verwendet, um sogenannte Presigned Urls für Datei(en) unter einem bestimmten Pfad im S3 zu erhalten. |

### Engine

> Die Event Bus Topics, die in dieser Dokumentation erläutert werden, spiegeln die Legacy-Elemente wider, die in der
> aktuellen Version der DigiWF Engine noch vorhanden sind.

Die digiwf-engine verwendet den Spring Cloud Stream Function Router, um Ereignisse basierend auf dem `type` Header an
die entsprechenden Spring Cloud Funktionen weiterzuleiten.
Daher benötigt jede Nachricht, die an `dwf-digiwf-engine-<ENV>` gesendet wird, den Header `type`.

```
dwf-digiwf-engine-<ENV>
```

Die folgenden Werte für den Header `type` werden derzeit unterstützt:

| Header Type         | Payload Type            | Beschreibung                                                                                                                                 |
|---------------------|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| correlateMessageV01 | `CorrelateMessageTOV01` | Verwenden Sie `CorrelateMessageTOV01` als Wert für den Header type, um eine Nachricht mit einer Prozessinstanz zu korrelieren.               |
| startProcessV01     | `StartInstanceTOV01`    | Verwenden Sie `startProcessV01` als Wert für den Header type, um eine neue Prozessinstanz mit dem Prozessschlüssel und den Daten zu starten. |

### CoCreation

> Die Kafka Topics, die in der DigiWF Cocreation verwendet werden, stammen aus früheren Entwicklungsphasen und werden
> bis zur geplanten Ablösung durch ein moderneres System beibehalten.

Prozesse, Entscheidungstabellen und JSON-Schema-Formulare, die in der Cocreation-Plattform entwickelt werden, werden mit
einem Deployment-Event, das an das Topic `dwf-cocreation-<ENV>` gesendet wird, in der digiwf-Engine deployed.

```
dwf-cocreation-<ENV>
```

Der digiwf-engine Service ist ein Consumer des Themas `dwf-cocreation-<ENV>` und stellt die Artefakte (bpmn-, dmn- oder
json-Form) aus eingehenden Bereitstellungsereignissen für die camunda-Engine bereit.
Die digiwf-engine verwendet den Spring Cloud Stream Function Router, um Events basierend auf dem Header `type` an die
entsprechenden Spring Cloud Funktionen weiterzuleiten.

Die folgenden Werte für den Header `type` werden derzeit unterstützt:

| Header Type  | Payload Type            | Beschreibung                                                                                                                 |
|--------------|-------------------------|------------------------------------------------------------------------------------------------------------------------------|
| deploy       | `DeploymentEvent`       | Verwenden Sie `deploy` als Wert für den Header type, um Prozesse und Entscheidungstabellen in der digiwf-Engine einzusetzen. |
| deploySchema | `SchemaDeploymentEvent` | Verwenden Sie `deploySchema` als Wert für den Header type, um JSON-Schema-Formulare an die digiwf-Engine zu übergeben.       |

```
dwf-cocreation-deploy-<ENV>
```

Nach jedem Deployment sendet die digiwf-engine ein Deployment-Status-Ereignis an das
Topic `dwf-cocreation-deploy-<ENV>`, das die Abonnenten des Topics über den Erfolg oder Misserfolg des Deployments
informiert.

