# Konfiguration

In diesem Abschnitt wird die Erstellung der Konfiguration für Prozesse behandelt.

::: tip
Momentan gibt es keinen grafischen Editor für die Erstellung von Konfigurationen. Wir empfehlen deshalb auf einen Editor
wie Notepad++ oder Visual Studio Code zurückzugreifen.
:::

``` json

{
  "key": "MeinProzessKey",
  "statusDokument": "d2d36a26-2df9-4044-8889-69bca2a2a715"
  "statusConfig": [
    {
      "key": "gestartet",
      "label": "Antrag gestellt",
      "position": 1
    },
    {
      "key": "genehmigung",
      "label": "Auf Genehmigung warten",
      "position": 2
    },
    {
      "key": "pruefen",
      "label": "Antrag prüfen",
      "position": 3
    },
    {
      "key": "fertig",
      "label": "Antrag abgeschlossen",
      "position": 4
    }
  ],
  "configs": [
    {
      "key": "ignore_fields_on_start",
      "value": "1"
    },
    {
      "key": "app_file_s3_async_config",
      "value": "my-s3-topic"
    },
    {
      "key": "app_file_s3_sync_config",
      "value": "http://my-document-storage-service:8080"
    },
      {
      "key": "app_instance_schema_key",
      "value": "myschemakey"
    },
    {
      "key": "app_instance_file_paths_readonly",
      "value": "docs"
    },
    {
      "key": "app_instance_file_paths",
      "value": "writabledocs"
    }
  ]
}

```

- **Key:** Der Key des Prozesses, für den die Konfiguration gültig ist
- **statusDokument:** Die ID des Statusdokuments, das für den Prozess verwendet werden soll. Dieses kann im Prozess über
  die Funktion ``processconfig.getStatusDokument()`` abgefragt werden
- **statusConfig:** Die Konfiguration der Statuswerte. Die Positionen der Statuswerte werden automatisch sortiert
- **configs:** Eine Key-/ Value-Map, die bei der Modellierung frei definiert und verwendet werden kann. Auf
  Konfigurationen kann im Prozess über die Funktion ``processconfig.get('configKey')`` zugegriffen werden.

In den configs können zudem DigiWF-spezifische Einstellungen angegeben werden:

- **ignore_fields_on_start:** Diese Einstellung ist optional und kann auf 1 oder 0 gesetzt werden. Wird die Einstellung
  über den Wert 1 aktiviert, dann wird beim Start eines Prozesses kein Fehler geworfen, wenn Daten mitgeschickt werden,
  die nicht im Formular enthalten sind. Stattdessen werden die Daten lediglich gefiltert und nicht in der Engine
  persistiert.
- **app_file_s3_async_config:** Wird die Einstellung gesetzt, dann wird der Wert als Konfiguration für den S3-Service
  verwendet. Dabei handelt es sich um das Topic für den verwendeten S3-Service. Die Konfig wird von der Plattform 
  autom. gesetzt und nur benötigt, wenn ein eigener S3-Service für die Ausführung des Prozesses verwendet werden soll.
- **app_file_s3_sync_config:** Wird die Einstellung gesetzt, dann wird der Wert als Konfiguration für den S3-Service
  verwendet. Dabei handelt es sich um die URL des verwendeten S3-Service. Die Konfig wird von der Plattform 
  autom. gesetzt und nur benötigt, wenn ein eigener S3-Service für die Ausführung des Prozesses verwendet werden soll.
- **app_instance_schema_key:** Das Schema, das in der Prozessinstanzübersicht verwendet werden soll, um Daten
  anzuzeigen.
- **app_instance_file_paths_readonly:** Die Dateipfade,die in der Prozessinstanzübersicht gelesen werden können
- **app_instance_file_paths:** Die Dateipfade, auf die in der Prozessinstanzübersicht geschrieben werden kann.
