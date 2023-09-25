# Task Management

Im Rahmen der Automatisierung und Digitalisierung von Prozessen wird task-orientierte Arbeitsweise eingeführt. Dabei werden 
durch das System einzelne Benutzeraufgaben erstellt und einem Kreis von Mitarbeitern zugewiesen. Die Aufgabe erscheint als 
ein Eintrag in einer **Aufgabenliste**. Beim Öffnen der Aufgabe wird dem Benutzer über ein **Aufgabenformular** der Kontext 
der Aufgabe angezeigt und für den Abschluss der Aufgabe notwendige Eingabefelder eingeblendet.


## Ist

### Aufgabenzuweisung (Assignment)

Die Zuweisung von Aufgaben an die Benutzer und Gruppen erfolgt zurzeit über:

- `Assignee`: Zuweisung einer Aufgabe an eine einzelne Person. Die `LHMObjectId` des Benutzers wird dazu in Camunda Assignee Feld geschrieben.
- `Candidate Users`: Änderung der Sichtbarkeit einer Aufgabe für eine Liste der einzeln bekannten Personen. Die komma-separierte Liste der `LHMObjectId` der Benutzers wird in Camunda Candidate Users Feld geschrieben.
- `Candidate Groups`: Änderung der Sichtbarkeit einer Aufgabe für Organisationseinheiten (LDAP OU). Die komma-separierte Liste der `OU` der Benutzers wird in Camunda Candidate Groups Feld geschrieben.

Die Aufgaben die persönlich zugewiesen sind, sind unter dem Abschnitt "Meine Aufgaben" des Benutzers zu finden. Die für einen 
Benutzer über die Nennung in den Candidate USers oder über die OU in Candidate Groups werden die Aufgaben im Abschnitt "Gruppenaufgaben" dargestellt. Die Bearbeitung einer Gruppenaufgabe weisst diese dem Benutzer zu (early claim).

### Schnellfilter

Suchen in der Taskliste können gespeichert werden, sodass ein Benutzer schnell zu den bereits getätigten Suchen zurückkehren kann. Z.Z. komplett über Frontend gelöst.

### Zurückstellen der Aufgaben

Durch das Setzen eines Nachverfolgungsdatums kann die Aufgabe "zurückgestellt" werden. Auf das Datum wird es im Frontend gefiltert.

### Abbrechen der Aufgaben

Eine Aufgabe kann abgebrochen werden (wirft ein BPMN Fehler).

### Zugriff auf Dateien

Input Variablen, die an einem User Task definiert sind, müssen aufgelöst werden müssen, um die Autorisierung der Pfade im S3 zu prüfen. (`app_files_paths`, `app_file_paths_readonly`).

## Soll

### Aufgabenzuweisung (Assignment)

In Zukunft (ab dem 01.04.2023) dürfen die Camunda Felder für Assignment nicht mehr verwendet werden.

**Szenarien**

- Eine Aufgabe wird erzeugt. Die Werte aus dem Prozessmodel müssen weiter verwendet werden. Idee -> Create Task listener schreibt Prozessvariablen, die für die Zuweisung verwendet werden.
- Eine Aufgabe sich selbst zuweisen. (Änderung des Assignees).
- Eine Aufgabenzuweisung entfernen (kann nur vom Bearbeiter, und nur wenn es Candidate Users oder Canndidate Groups gibt, damit die Aufgabe noch auffindbar ist).
- Reassign (nimm einem anderen eine Aufgabe weg)
- Dispatch (eine Zuweisung an anderen), wird über eine Sondervariable abgebildet (pro task oder pro instanz).

**Variablen zur Abbildung**

Lokale Task Variable:

```json
{
    "task_metadata": {
        "assignment": { "assignee": "9182719832", "candidateUsers": "9182719832, 9182719823", "candidateGroups": "itm-km8", "dispatcher": "198273491" },
        "tags": {}
    }
   
}
```

### Klassifikation von Aufgaben

tbd.

# Technische Architektur

Das folgende Diagram verdeutlicht den Gesamtzusammenhang:

![Task Management Architekturübersicht](~@source/images/platform/components/task-connector.png)

## Task Engine Connector

Eine oder mehrere Prozessengines erzeugen während der Ausführung Benutzeraufgaben, die von Benutzern abgearbeitet werden. Dazu müssen 
die Aufgaben von den Engines in zentrale Task Management transportiert werden. Die Aufgaben werden mit einem speziellen Engine-Connector
eingesammelt, mit den Metadaten eingereichert und an das Kafka Topic `dwf-taskmanagement-tasks-<STAGE>` versandt. Dabei werden
`polyflow-connector` und `polyflow-core` Komponenten auf der Engine-Seite deployed. 

Besonders zu beachten ist hier, dass durch die Nutzung von Axon die Kommunikation der Task-Events anders als die restliche Plattform
funktioniert. Alle [Events](https://docs.axoniq.io/reference-guide/axon-framework/events) zu den Tasks werden zunächst in der 
Datenbank in der Tabelle `domain_event_entry` abgelegt. Von dort aus greift ein [Kafka-Consumer](https://docs.axoniq.io/reference-guide/extensions/kafka)
die Events ab und schickt sie über Kafka an das Task Management. Damit der Stand des Kafka-Consumers in der Tabelle nicht verloren geht,
wird in der `token_entry` Tabelle der [Status gespeichert](https://docs.axoniq.io/reference-guide/axon-framework/events/event-processors/streaming#token-store).
Sollte es mehrere Instanzen des Connectors geben, kann nur eine von denen den Token führen, 
sodass eine [Mehrfachverarbeitung](https://docs.axoniq.io/reference-guide/axon-framework/events/event-processors/streaming#tracking-tokens) verhindert wird.

## Task Management

Das Task Management sammelt die Aufgaben, die über den Kafka Topic `dwf-taskmanagement-tasks-<STAGE>` ankommen und stellt diese 
über eine REST Schnittstelle der Task Liste bereit. Dabei werden die Taskformulare und die Taskdaten an die Task Liste übermittelt. 
Wenn der Task abgeschlossen wird, übernimmt das Task Management die Validierung der Daten und schickt diese im Erfolgsfall via
REST an die Prozess Engine.

Bei der Verarbeitung der Aufgaben über Kafka ist zu beachten, 
dass [Axon keine Consumer-Groups nutzt](https://docs.axoniq.io/reference-guide/extensions/kafka#consuming-events-with-a-streamable-message-source).
Der Stand innerhalb des Event-Streams wird mit einem Token in der Datenbank verfolgt und damit über mehrere Instanzen synchronisiert.  

## Anbindung Security

Entsprechend der Referenzarchitektur findet die Anbindung der Frontends via ein Reverse-Proxy Gateway statt. Das Gateway sorgt für
die Anfrage und Zwischenspeicherung des Access Tokens und übermittelt diesen an das Backend. Aus diesem Access Token wird das 
`lhmObjectID` Claim gelesen und gegen das LDAP überprüft. 

Bei der Kommunikation zwischen den Systemen wird ein technischer Benutzer (Service Account der Anwendung) genutzt. Um die
Identität des aufrufenden Benutzers zu transportieren, wird ein zusätzlicher spezieller HTTP Header `X-Authorization-Username` 
verwendet. Der Header trägt den Benutzernamen des Aufrufbenutzers (LDAP `lhmObjectID`). 
