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

## Formular modellieren

![Element Template](~@source/modeling/guides/modeling-first-process/form-create.png)

## Formular verknüpfen
Anschließend kann das Formular mit dem Prozess verknüpft werden.
Dabei muss die ID des Formulars im Feld `Form Key` des Startereignisses und im Form hinterlegt werden.
Zudem kann der `Formular Key`

::: tip
Genauer Informationen sind im Kapitel [Formulare](/modeling/forms/#formulare-mit-bpmn-modellen-verknupfen) zu finden.
:::


## Deployment

## Autorisierung

## Ausführung