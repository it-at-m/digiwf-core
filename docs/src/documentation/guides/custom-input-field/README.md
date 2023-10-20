# Hinzufügen von eigenen Eingabeelementen

In DigiWF ist es möglich eigene Eingabefelder zu definieren. 
Dazu ist auf dieser Seite dokumentiert welche Schritte zu erfolgen sind.

## 1. Anlegen eines NPM packages für die Komponente

1. In `/digiwf-app/packages/components` wird ein neues NPM Projekt nach dem Vorbild der bestehenden angelegt.
2. `lerna.json` wird aktualisiert und das neue NPM Package ergänzt

Wichtig ist, dass kontrolliert wurde, dass der Packagename richtig gesetzt wird.

## 2. Erweitern der Formulareinstellungen

Die Formulareinstellungen sind unter `/digiwf-app/packages/components/digiwf-form-builder-settings` zu finden.

Grundsätzlich ist zu beachten, dass Änderungen im Ordner `de` und `en` gleichermaßen vorgenommen werden müssen.

### Fall 1: Änderung Eingabefeld für ein bestehenden Eingabetypen

Im Fall, dass ein vorhandenes Eingabefeld überschrieben werden soll, 
sucht man als Erstes die Definition von entsprechendem Formfield in der `FormFields.ts`.
Anschließend fügt man die Property "x-display" mit dem Komponentenreferenzschlüssel ein. Dieser ist frei wählbar. 
Beispiele für das Vorgehen ist die Zeit oder Datumseingabe. 

Beispiel X-Options: 
```json5
{
//  ...
  "FormField_time": {
    "fieldType": "time",
    "x-display": "custom-time-input",
    "title": "Time",
    "type": "string",
    "format": "time",
    // ...
  },
  //  ...
}
```
Anschließend fügt man die Property in die `ModelerPalette.ts` in dem entsprechenden Eintrag ein.

### Fall 2: Hinzufügen eines Eingabefelds für einen neuen Datentypen

Man fügt in der `FormFields.ts` Datei einen entsprechenden neuen Eintrag ein 
und exportiert diesen in der "schemaMap" am Ende der Datei. 
Als Orientierung dafür kann die `multi-user-input` Komponente genutzt werden.

Anschließend fügt man einen neuen Eintrag in die `ModelerPalette.ts` ein.

## Registrieren der Vue.Js Komponente für das Rendering

Bisher ist durch das Erweitern des Schemas nur die Möglichkeit geschaffen worden eigene Komponenten zu erstellen. 
Der Formrenderer kennt diese aber nicht. 
Dafür müssen wir den Key von `x-display` noch mit der Vue.Js Komponente verbinden. 

In `digiwf-forms-example` geschieht dies in der `App.vue` Datei. 
In dieser ist das Vorgehen auch anhand der vorhandenen Komponenten zu erkennen. 

In der Tasklist muss dies auf gleichen Weg in zwei Dateien gemacht werden: 
1. src/components/schema/AppJsonForm.vue
2. src/components/schema/AppJsonRenderer.vue

## Erweitern des all-input-fields Prozesses

Für das Testen der Komponente sollte das all-inputs-field Formschema
(zu finden im Projektpfad: `digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/all-input-fields/all-inputs.schema.json`).
um die Komponente erweitert werden.
