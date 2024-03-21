# Migration von Formularen V1 auf V2

::: warning
Die Migration von Formularen V1 auf V2 muss manuell durchgeführt werden.
In dieser Anleitung werden die wichtigsten Besonderheiten aufgeführt
und eine Schritt-für-Schritt-Anleitung bereitgestellt.
:::

## Schritt-für-Schritt-Anleitung

### 1. Projekt in der Co-Creation anlegen

Falls noch nicht geschehen, müssen Prozesse, die mit dem alten Formularsystem umgesetzt sind, zunächst in der
Co-Creation angelegt werden.

Hierzu kann die Anleitung [Prozess in der Co-Creation anlegen](../co-creation/README.md) verwendet werden.

Im Anschluss sollte der Prozess in das Projekt hochgeladen werden.

### 2. Formular in VS Code öffnen

Formulare wurden über das `Form Tab` im BPMN Modeler konfiguriert.
Darin ist der Key des Formulars enthalten, der in der Datenbank gespeichert ist.
Sollte das Formular lediglich in der Datenbank der Engine vorhanden sein, muss es zunächst in VS Code geöffnet werden.

### 3. Formular in Co-Creation anlegen

::: tip
Es empfiehlt sich, ein Formular vollständig zu migrieren, bevor man mit anderen Formularen fortfährt.
Oft haben Formulare Überschneidungen, sodass die Migration durch *Copy&Paste* beschleunigt werden kann.
:::

### 4. Prozess importieren

tbd.

### 5. User Task neu modellieren

tbd.

## Dokumentenfelder

::: warning
Sollten ein oder mehrere Dokumentenfelder im alten Formular vorhanden sein, muss zusätzlich eine Migration der Daten zu
JSON durchgeführt werden.
:::

### document-input

tbd.

### alw-document-input

tbd.

### Datenmigration

Um Daten zu migrieren steht ein ``Admin Prozess`` bereit, der vom DigiWF Core Team ausgeführt werden kann.
Hierfür werden folgende Informationen benötigt:

- Key des Prozesses
- Liste der Formularfelder(keys), die migriert werden sollen

tbd.

## Abbruch modellieren

tbd.

## Statusdokument generieren

tbd.