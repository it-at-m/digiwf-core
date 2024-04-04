# Hinzufügen eigener Eingabeelemente

In DigiWF können eigene Eingabefelder definiert werden. Hier wird beschrieben, welche Schritte dazu erforderlich sind.

## 1. Anlegen eines NPM packages für die Komponente

1. In `/digiwf-app/packages/components` wird ein neues NPM-Projekt nach dem Vorbild der bereits vorhandenen Projekte
   angelegt.
2. `lerna.json` wird aktualisiert und das neue NPM-Paket ergänzt.

Wichtig ist, dass sichergestellt wird, dass der Package-Name korrekt gesetzt ist.

## 2. Erweitern der Formulareinstellungen

Die Formulareinstellungen befinden sich unter `/digiwf-app/packages/components/digiwf-form-builder-settings`.

Es ist zu beachten, dass Änderungen sowohl im Ordner `de` als auch im Ordner `en` vorgenommen werden müssen.

### Fall 1: Änderung eines Eingabefelds für einen vorhandenen Eingabetyp

Wenn ein vorhandenes Eingabefeld überschrieben werden soll, sucht man zuerst die Definition des entsprechenden
Formularfelds in der `FormFields.ts`. Anschließend fügt man die Property "x-display" mit dem
Komponentenreferenzschlüssel ein. Dieser Schlüssel ist frei wählbar. Beispiele dafür sind die Zeit- oder Datumseingabe.

Beispiel für X-Options:

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
Danach fügt man die Property in die `ModelerPalette.ts` in dem entsprechenden Eintrag ein.

### Fall 2: Hinzufügen eines Eingabefelds für einen neuen Datentyp

Man fügt in der `FormFields.ts` Datei einen entsprechenden neuen Eintrag ein und exportiert diesen in der "schemaMap" am
Ende der Datei. Als Orientierung dafür kann die `multi-user-input` Komponente genutzt werden.

Anschließend fügt man einen neuen Eintrag in die `ModelerPalette.ts` ein.

## Registrierung der Vue.js-Komponente für das Rendering

Durch das Erweitern des Schemas wurde bisher nur die Möglichkeit geschaffen, eigene Komponenten zu erstellen. Der
Formrenderer erkennt diese jedoch nicht. Dafür muss der Schlüssel von `x-display` noch mit der Vue.js-Komponente
verbunden werden.

In `digiwf-forms-example` wird dies in der `App.vue` Datei durchgeführt. Anhand der vorhandenen Komponenten kann das
Vorgehen nachvollzogen werden.

In der Tasklist müssen dieselben Schritte in zwei Dateien ausgeführt werden:

1. src/components/schema/AppJsonForm.vue
2. src/components/schema/AppJsonRenderer.vue

## Erweitern des "all-input-fields"-Prozesses

Zum Testen der Komponente muss das "all-inputs-field"-Formschema (zu finden im
Projektpfad: `digiwf-engine/digiwf-engine-service/src/main/resources/prozesse/example/all-input-fields/all-inputs.schema.json`)
um die Komponente erweitert werden.