# Einführung

DigiWF setzt bei der Modellierung von Formularen auf die Technologie JSON Schema. JSON Schema ist eine Sprache, die es
ermöglicht, die Struktur von JSON-Daten zu beschreiben.

Die verwendete Rendering Bibliothek, die erweitert wurde,
ist [VJSF](https://koumoul-dev.github.io/vuetify-jsonschema-form/latest/). VJSF ist eine Vue.js Bibliothek, die es
ermöglicht aus JSON Schema Vuetify Formulare zu generieren.

Für die Modellierung von Formularen steht ein Editor zur Verfügung, der es ermöglicht, Formulare per Drag & Drop zu
erstellen.

## Editor

## Formularstruktur

Formulare bestehen aus folgenden Elementen:

- Abschnitt: Ein Abschnitt kann in der Modellierung als Container für Gruppen verwendet werden. Über Abschnitte kann in
  der Konfiguration des Editors bspw. ein Stepper konfiguriert werden.
- Gruppe: Eine Gruppe kann in der Modellierung als Container für Felder verwendet werden. 