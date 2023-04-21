# Ersten Prozess modellieren

Um einen ersten Prozess in DigiWF umzusetzen sollten folgende Voraussetzungen erfüllt sein:

- Kenntnisse in der Sprache BPMN
- Zugriff auf die Entwicklungsumgebung
- Zugriff auf das Camunda Cockpit in der jeweiligen Umgebung

## Prozess anlegen

Um einen Prozess anlegen zu können, muss zunächst ein Projekt inder Entwicklungsumgebung angelegt werden.
Eine kurze Beschreibung dazu findet sich unter [Projekt anlegen](/modeling/plattform/project/#projekt-anlegen).

![Element Template](~@source/modeling/guides/modeling-first-process/project-create.png)

Danach kann ein Prozess als neues Artefakt dem Projekt hinzugefügt werden.
Eine kurze Beschreibung dazu findet sich unter [Artefakt hinzufügen](/modeling/plattform/artefact/#artefakt-hinzufugen).

![Element Template](~@source/modeling/guides/modeling-first-process/artefact-create.png)

## Prozess modellieren

Um den Prozess zu modellieren, muss auf das Artefakt geklickt werden.

![Element Template](~@source/modeling/guides/modeling-first-process/artefact-open-process.png)

Im BPMN Modeller kann nun ein Prozess modelliert werden.
Im ersten Schritt ist es ausreichend, ein Startereignis, eine Benutzeraufgabe und ein Endereignis zu modellieren.

![Element Template](~@source/modeling/guides/modeling-first-process/process-create.png)

Im Modeler können nun die einzelnen Elemente mit den entsprechenden Eigenschaften versehen werden.
Zunächst sollte auf Prozessebene die Id und der Name gesetzt werden.
Der Name kann beliebig ersetzt werden, dabei sollte auf die eindeutigkeit der ID geachtet werden.

![Element Template](~@source/modeling/guides/modeling-first-process/process-properties.png)

::: tip
Weitere Details zur Modellierung von Prozessen finden sich unter [Prozessmodellierung](/modeling/processes/modeling/).
:::

## User Task konfigurieren

Über die Palette kann ein Task hinzugefügt und als User Task konfiguriert werden.
Im Anschluss sollte das ``Basic: Usertask`` Element Template ausgewählt werden.

![Element Template](~@source/modeling/guides/modeling-first-process/task-create.png)

Zudem kann der Assignee konfiguriert werden.
Ich kann auf die Variable ``starterOfInstance`` zurückgegriffen werden.
Dadurch wird die Aufgabe der Person zugewiesen, die den Prozess gestartet hat.

![Element Template](~@source/modeling/guides/modeling-first-process/task-properties.png)

## Formular modellieren

Zunächst muss ein Formular vom Typ `FORM` angelegt werden.

![Element Template](~@source/modeling/guides/modeling-first-process/form-create.png)

Im Anschluss kann das Formular mit den entsprechenden Feldern versehen werden.

![Element Template](~@source/modeling/guides/modeling-first-process/form-properties.png)

::: tip
Genauere Informationen, zur Formularerstellung, sind im Kapitel [Formulare](/modeling/forms/) zu finden.
:::

## Formular verknüpfen

Anschließend kann das Formular mit dem Prozess verknüpft werden.
Dabei muss die ID des Formulars im Feld `Form Key` des Startereignisses und im Form hinterlegt werden.
Zudem kann der `Formular Key` des zuvor erstellen `User Tasks` mit der ID des Formulars verknüpft werden.

::: tip
Genauer Informationen sind im Kapitel [Formulare](/modeling/forms/#formulare-mit-bpmn-modellen-verknupfen) zu finden.
:::

## Deployment

Im Anschluss können die Artefakte auf die entsprechende Umgebung deployed werden.
Eine kurze Beschreibung dazu findet sich unter [Deployment](/modeling/plattform/deployment/).

## Autorisierung

Ein Prozess wird in der jeweiligen DigiWF-Oberfläche erst sichtbar, wenn die entsprechenden Berechtigungen in Camunda
gesetzt sind.
Berechtigungen müssen durch das entsprechende Camunda Team mit Zugriff auf das Cockpit gesetzt werden.
Die Dokumentation ist unter [Autorisierung](/modeling/processes/authorization/#autorisierung-von-prozessen) zu finden.

## Ausführung

Im Anschluss kann der Prozess gestartet werden.
Dazu kann untern ``Vorgang starten`` der Prozess anhand des Namens gesucht und geöffnet werden.

![Element Template](~@source/modeling/guides/modeling-first-process/process-start.png)

Es öffnet sich das Formular, welches im vorherigen Schritt erstellt wurde.

![Element Template](~@source/modeling/guides/modeling-first-process/process-form.png)

Dieses kann entsprechend ausgefüllt und der Prozess gestartet werden.
Im Anschluss gibt es unter ``Meine Aufgaben`` eine neue Aufgabe.
Sollte dies nicht der Fall sein wurde vergessen im ``User Task`` den Assignee auf `${starterOfInstance}` zu setzen.
Mit einem Klick auf die Aufgabe kann diese geöffnet und bearbeitet werden.

![Element Template](~@source/modeling/guides/modeling-first-process/task-open.png)
