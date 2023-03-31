# Einführung

DigiWF setzt bei der Modellierung von Formularen auf die Technologie JSON Schema. JSON Schema ist eine Sprache, die es
ermöglicht, die Struktur von JSON-Daten zu beschreiben.

Die verwendete Rendering Bibliothek, die erweitert wurde,
ist [VJSF](https://koumoul-dev.github.io/vuetify-jsonschema-form/latest/). VJSF ist eine Vue.js Bibliothek, die es
ermöglicht aus JSON Schema Vuetify Formulare zu generieren.

Für die Modellierung von Formularen steht ein Editor zur Verfügung, der es ermöglicht, Formulare per Drag & Drop zu
erstellen.

## Editor

Der Formulareditor besteht aus 3 Abschnitten:

- **Builder:** Hier können Formulare per Drag & Drop erstellt werden.
- **Vorschau:** Anzeige des erstellen Formulars.
- **Konfiguration:** Konfiguration des Formulars. Hier kann die Id kopiert und die Visualisierung des Formulars geändert
  werden.

![Formular Editor](~@source/modeling/forms/form-editor.png)

## Formularstruktur

Formulare bestehen aus folgenden Elementen:

- **Abschnitt:** Ein Abschnitt kann in der Modellierung als Container für Gruppen verwendet werden. Über Abschnitte kann
  in
  der Konfiguration des Editors bspw. ein Stepper konfiguriert werden.
- **Gruppe:** Eine Gruppe kann in der Modellierung als Container für Felder verwendet werden.
- **Feld:** Eine Eingabemaske für einen Wert. Hierfür stehen unterschiedlichen Typen zur Verfügung, die im
  Abschnitt [Felder](/modeling/forms/fields/) beschrieben sind.

![Formular Struktur](~@source/modeling/forms/form-structure.png)